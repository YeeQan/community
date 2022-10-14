package com.styeeqan.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.DictField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.util.*;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.mapper.UserMapper;
import com.styeeqan.community.pojo.po.Dict;
import com.styeeqan.community.pojo.po.User;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSev {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DictUtil dictUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private PinYinUtil pinYinUtil;

    /**
     * 用户注册
     *
     * @param account 账号
     * @param username 昵称
     * @param password 密码
     * @param response response
     * @throws Exception Exception
     */
    public void register(String account, String username, String password, HttpServletResponse response) throws Exception {

        User userDB = userMapper.selectById(account);

        // 账号已存在
        if (userDB != null) {
            throw new CustomizeException(ServerStatusCode.ACCOUNT_EXIST);
        }

        // 昵称已存在
        if (userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", username)) != null) {
            throw new CustomizeException(ServerStatusCode.USERNAME_EXIST);
        }

        // 避免重复注册
        synchronized (this) {
            // 新增用户信息
            User user = new User();
            user.setAccount(account);
            user.setUserInfoId(commonUtil.uuid());
            user.setHomepageId(pinYinUtil.toPinyin(username));
            user.setCreateTime(new Date());
            user.setCreateUser(account);
            user.setUpdateTime(new Date());
            user.setUpdateUser(account);
            userMapper.insert(user);
            // 插入用户个人资料信息
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getUserInfoId());
            userInfo.setAccount(account);
            userInfo.setUsername(username);
            // 对用户密码做 MD5 加密
            String pwdMD5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            userInfo.setPassword(pwdMD5);
            List<Dict> dictList = dictUtil.getDictByType(DictField.Type.user_default_head_portrait);
            // 随机抽取一张默认头像
            if (!CollectionUtils.isEmpty(dictList)) {
                Dict dict = dictList.get((int) (Math.random() * dictList.size()));
                userInfo.setHeadPortrait(dict.getValue());
            }
            // 设置默认性别
            Optional<Dict> dictOpt = dictUtil.getDict(DictField.Dict.user_sex_unknown);
            if (dictOpt.isPresent()) {
                Dict dict = dictOpt.get();
                userInfo.setSex(dict.getValue());
            } else {
                userInfo.setSex(DictField.Dict.user_sex_unknown.defaultValue);
            }
            userInfo.setCreateTime(new Date());
            userInfo.setCreateUser(user.getAccount());
            userInfo.setUpdateTime(new Date());
            userInfo.setUpdateUser(user.getAccount());
            userInfoMapper.insert(userInfo);

            // 设置 token
            Map<String, String> payloadMap = new HashMap<>(2);
            payloadMap.put(CommonField.ACCOUNT, account);
            Optional<String> tokenOpt = jwtUtil.getToken(payloadMap);
            if (tokenOpt.isPresent()) {
                String token = tokenOpt.get();
                Cookie cookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 7);
                response.addCookie(cookie);
            }
        }
    }

    public void login(String account, String password, HttpServletResponse response) {

        User userDB = userMapper.selectById(account);

        // 获取用户信息，用户不存在则返回错误提示
        if (userDB == null) {
            throw new CustomizeException(ServerStatusCode.ACCOUNT_NOT_EXIST);
        } else {
            UserInfo userInfoDB = userInfoMapper.selectById(userDB.getUserInfoId());
            // 校验密码
            if (userInfoDB == null ||
                    !userInfoDB.getPassword()
                            .equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
                throw new CustomizeException(ServerStatusCode.PASSWORD_ERROR);
            }
        }

        // 设置 token
        Map<String, String> payloadMap = new HashMap<>(2);
        payloadMap.put(CommonField.ACCOUNT, account);
        Optional<String> tokenOpt = jwtUtil.getToken(payloadMap);
        if (tokenOpt.isPresent()) {
            String token = tokenOpt.get();
            Cookie cookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 7);
            response.addCookie(cookie);
        }
    }

    public UserVO getLoginInfo(String account) {

        UserVO userVO = new UserVO();

        UserInfo userInfoDB = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", account));
        if (userInfoDB != null) {
            userVO.setUsername(userInfoDB.getUsername());
            userVO.setHeadPortrait(userInfoDB.getHeadPortrait());
        }

        User userDB = userMapper.selectById(account);
        if (userDB != null) {
            userVO.setHomepageId(userDB.getHomepageId());
            userVO.setCreateTime(userDB.getCreateTime());
        }

        return userVO;
    }

    public void logout(HttpServletResponse response) {
        // 设置 Cookie 失效
        Cookie cookie = cookieUtil.getCookie(CommonField.TOKEN, null, 0);
        response.addCookie(cookie);
    }
}