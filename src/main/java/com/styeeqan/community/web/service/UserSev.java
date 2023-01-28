package com.styeeqan.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.DictField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.*;
import com.styeeqan.community.mapper.UserInfoMapper;
import com.styeeqan.community.mapper.UserMapper;
import com.styeeqan.community.pojo.dto.UserDto;
import com.styeeqan.community.pojo.po.Dict;
import com.styeeqan.community.pojo.po.User;
import com.styeeqan.community.pojo.po.UserInfo;
import com.styeeqan.community.pojo.vo.UserHomepageVo;
import com.styeeqan.community.pojo.vo.UserVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RsaUtil rsaUtil;

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
                Cookie cookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 365 * 10);
                response.addCookie(cookie);
                // 在 Redis 中保存
                redisUtil.setValue(RedisKey.USER_TOKEN, account, token);
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
            Cookie cookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 365 * 10);
            response.addCookie(cookie);
            // 在 Redis 中保存
            redisUtil.setValue(RedisKey.USER_TOKEN, account, token);
        }
    }

    public UserVo getLoginInfo(String account) {

        UserVo userVO = new UserVo();

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

    public UserHomepageVo loadHomepage(String homepageId, String account) {

        UserHomepageVo userHomepageVO = new UserHomepageVo();

        User userDB = userMapper.selectOne(new QueryWrapper<User>().eq("homepage_id", homepageId));
        if (userDB == null) {
            throw new CustomizeException(ServerStatusCode.UNKNOWN);
        }

        // 是否本人主页
        if (userDB.getAccount().equals(account)) {
            userHomepageVO.setSelf(Boolean.TRUE);
        }

        UserInfo userInfoDB = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", userDB.getAccount()));
        if (userInfoDB != null) {
            userHomepageVO.setUserInfoId(userInfoDB.getId());
            userHomepageVO.setJoinTime(userInfoDB.getCreateTime());
            userHomepageVO.setHeadPortrait(userInfoDB.getHeadPortrait());
            userHomepageVO.setUsername(userInfoDB.getUsername());
        }

        return userHomepageVO;
    }

    public UserVo getUserInfo(String account) {

        UserVo userVO = new UserVo();

        UserInfo infoDB = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("account", account));

        if (infoDB != null) {
            userVO.setUsername(infoDB.getUsername());
            userVO.setHeadPortrait(infoDB.getHeadPortrait());
            userVO.setBirthday(infoDB.getBirthday());
            List<Dict> dictList = dictUtil.getDictByType(DictField.Type.user_sex);
            for (Dict dict : dictList) {
                if (dict.getValue().equals(infoDB.getSex())) {
                    userVO.setSex(dict.getLabel());
                    break;
                }
            }
            userVO.setCity(infoDB.getCity());
            userVO.setIntroduction(infoDB.getIntroduction());
            userVO.setSchool(infoDB.getSchool());
            userVO.setMajor(infoDB.getMajor());
            userVO.setCompany(infoDB.getCompany());
            userVO.setPosition(infoDB.getPosition());
        }

        return userVO;
    }

    public UserVo saveUserInfo(UserDto userDTO) {

        UserVo userVO = new UserVo();

        UserInfo userInfo = new UserInfo();

        User user = userMapper.selectById(userDTO.getAccount());

        userInfo.setId(user.getUserInfoId());
        userInfo.setUsername(userDTO.getUsername());
        userInfo.setBirthday(dateUtil.parseDate(userDTO.getBirthday(), DateUtil.parse_date_pattern_1));
        userInfo.setSex(userDTO.getSex());
        userInfo.setCity(userDTO.getCity());
        userInfo.setIntroduction(userDTO.getIntroduction());
        userInfo.setSchool(userDTO.getSchool());
        userInfo.setMajor(userDTO.getMajor());
        userInfo.setCompany(userDTO.getCompany());
        userInfo.setPosition(userDTO.getPosition());

        if (userInfoMapper.updateById(userInfo) > 0) {
            userVO.setUsername(userInfo.getUsername());
            userVO.setBirthday(userInfo.getBirthday());
            userVO.setSex(userInfo.getSex());
            userVO.setCity(userInfo.getCity());
            userVO.setIntroduction(userInfo.getIntroduction());
            userVO.setSchool(userInfo.getSchool());
            userVO.setMajor(userInfo.getMajor());
            userVO.setCompany(userInfo.getCompany());
            userVO.setPosition(userInfo.getPosition());
        }

        return userVO;
    }

    @SneakyThrows
    public String uploadHeadPortrait(String account, MultipartFile multipartFile) {

        String url = null;

        // 参数不能为空
        if (multipartFile == null && multipartFile.isEmpty()) {
            throw new CustomizeException(ServerStatusCode.PARAM_ERROR);
        }

        // 必须是图片类型
        String contentType = multipartFile.getContentType();
        if (!("image/png".equals(contentType) || "image/jpg".equals(contentType) || "image/jpeg".equals(contentType))) {
            throw new CustomizeException(ServerStatusCode.IMAGE_TYPE_ERROR);
        }

        // 图片不能大于2M
        if (multipartFile.getSize() > 2 * 1024 * 1024) {
            throw new CustomizeException(ServerStatusCode.IMAGE_SIZE_ERROR);
        }

        // 上传Aliyun oss
        Optional<String> optional = aliyunOssUtil.upload("static/headPortrait/" + account + "." + contentType.split("/")[1], multipartFile.getInputStream());

        // 获取返回结果
        if (optional.isPresent()) {
            url = optional.get();
        }

        // 更新头像信息
        if (!StringUtils.isEmpty(url)) {
            userInfoMapper
                    .update(new UserInfo().setHeadPortrait(url),
                            new QueryWrapper<UserInfo>().eq("account", account));
        } else {
            throw new CustomizeException(ServerStatusCode.UNKNOWN);
        }

        return url;
    }
}