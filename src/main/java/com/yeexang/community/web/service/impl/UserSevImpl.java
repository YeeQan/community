package com.yeexang.community.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.constant.DictField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.response.AliyunOssResult;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.http.response.SevFuncResult;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.util.AliyunOssUtil;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.DictUtil;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Dict;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.TopicVO;
import com.yeexang.community.pojo.vo.UserVO;
import com.yeexang.community.web.service.impl.base.BaseSev;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSevImpl extends BaseSev<User, String> implements UserSev {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DictUtil dictUtil;

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Override
    protected RedisKey getRedisKey() {
        return RedisKey.USER;
    }

    @Override
    protected BaseMapper getBaseMapper() {
        return userDao;
    }

    @Override
    protected Class getEntityClass() {
        return User.class;
    }

    @Override
    public SevFuncResult register(UserDTO userDTO) {
        SevFuncResult sevFuncResult;
        try {
            // 账号已存在
            if (selectById(userDTO.getAccount()) != null) {
                return new SevFuncResult(false, "账号已存在", ServerStatusCode.ACCOUNT_EXIST);
            }
            // 昵称已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", userDTO.getUsername());
            User userDB = userDao.selectOne(queryWrapper);
            if (userDB != null) {
                return new SevFuncResult(false, "账号已存在", ServerStatusCode.USERNAME_EXIST);
            }
            Optional<BasePO> optional = userDTO.toPO();
            if (optional.isPresent()) {
                selectById(userDTO.getAccount());
                User user = (User) optional.get();
                // 避免重复注册
                synchronized (this) {
                    user.setId(commonUtil.uuid());
                    // 对用户密码做 MD5 加密
                    String pwd = userDTO.getPassword();
                    String pwdMD5 = DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
                    user.setPassword(pwdMD5);
                    List<Dict> dictList = dictUtil.getDictByType(DictField.USER_DEFAULT_HEAD_PORTRAIT);
                    // 随机抽取一张默认头像
                    if (!dictList.isEmpty()) {
                        Dict dict = dictList.get((int) (Math.random() * dictList.size()));
                        user.setHeadPortrait(dict.getValue());
                    }
                    user.setCreateTime(new Date());
                    user.setCreateUser(user.getAccount());
                    user.setUpdateTime(new Date());
                    user.setUpdateUser(user.getAccount());
                    user.setDelFlag(false);
                    save(user, user.getAccount());
                    sevFuncResult = new SevFuncResult(true, "成功", ServerStatusCode.SUCCESS);
                }
            } else {
                return new SevFuncResult(false, "未知错误", ServerStatusCode.UNKNOWN);
            }
        } catch (Exception e) {
            log.error("UserSev register errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new SevFuncResult(false, "未知错误", ServerStatusCode.UNKNOWN);
        }
        return sevFuncResult;
    }

    @Override
    public SevFuncResult login(UserDTO userDTO) {
        SevFuncResult sevFuncResult;
        try {
            // 获取用户信息，用户不存在则返回错误提示
            User user = selectById(userDTO.getAccount());
            if (user == null) {
                sevFuncResult = new SevFuncResult(false, "用户不存在", ServerStatusCode.ACCOUNT_NOT_EXIST);
            } else {
                // 校验密码
                if (!user.getPassword()
                        .equals(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes(StandardCharsets.UTF_8)))) {
                    sevFuncResult = new SevFuncResult(false, "密码错误", ServerStatusCode.PASSWORD_ERROR);
                } else {
                    sevFuncResult = new SevFuncResult(true, "成功", ServerStatusCode.SUCCESS);
                }
            }
        } catch (Exception e) {
            log.error("UserSev login errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new SevFuncResult(false, "未知错误", ServerStatusCode.UNKNOWN);
        }
        return sevFuncResult;
    }

    @Override
    public List<TopicVO> getTopicListByAccount(String account) {
        List<TopicVO> topicVOList;
        try {
            User user = selectById(account);
            QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("create_user", account);
            List<Topic> topicList = topicDao.selectList(queryWrapper);
            topicVOList = topicList.stream()
                    .map(topic -> {
                        TopicVO vo = null;
                        Optional<BaseVO> optional = topic.toVO();
                        if (optional.isPresent()) {
                            vo = (TopicVO) optional.get();
                            // 设置用户名和头像
                            vo.setCreateUserName(user.getUsername());
                            vo.setHeadPortrait(user.getHeadPortrait());
                        }
                        return vo;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("UserSev getTopicListByAccount errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return topicVOList;
    }

    @Override
    public SevFuncResult uploadHeadPortrait(MultipartFile file, String account) {
        SevFuncResult sevFuncResult;
        try {
            // 上传阿里云 oss
            AliyunOssResult aliyunOssResult = aliyunOssUtil.uploadHeadPortrait(file, account);
            if (aliyunOssResult.isSuccess()) {
                // 更新用户头像信息
                User user = new User();
                user.setAccount(account);
                user.setHeadPortrait(aliyunOssResult.getUrl());
                save(user, account);
                sevFuncResult = new SevFuncResult(true, "成功", ServerStatusCode.SUCCESS);
            } else {
                sevFuncResult = new SevFuncResult(true, "成功", ServerStatusCode.FILE_UPLOAD_FAILED);
            }
        } catch (Exception e) {
            log.error("UserSev uploadHeadPortrait errorMsg: {}", e.getMessage(), e);
            sevFuncResult = new SevFuncResult(true, "未知错误", ServerStatusCode.UNKNOWN);
        }
        return sevFuncResult;
    }

    @Override
    public Optional<UserVO> getUserVOByAccount(String account) {
        UserVO userVO = null;
        try {
            User user = selectById(account);
            if (user != null) {
                Optional<BaseVO> baseVOptional = user.toVO();
                if (baseVOptional.isPresent()) {
                    userVO = (UserVO) baseVOptional.get();
                }
            }
        } catch (Exception e) {
            log.error("UserSev getUserVOByAccount errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(userVO);
    }
}