package com.yeexang.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.DictField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.response.AliyunOssResult;
import com.yeexang.community.common.http.response.SevFuncResult;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.task.UserRegisterTask;
import com.yeexang.community.common.util.*;
import com.yeexang.community.dao.*;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.*;
import com.yeexang.community.pojo.po.ext.UserHomepageExt;
import com.yeexang.community.pojo.vo.*;
import com.yeexang.community.web.service.base.BaseSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
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
public class UserSev extends BaseSev<User, String> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserHomepageDao userHomepageDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserDynamicDao userDynamicDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private DictUtil dictUtil;

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Autowired
    private ThreadUtil threadUtil;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.USER;
    }
    
    @Override
    public BaseMapper getBaseMapper() {
        return userDao;
    }

    @Override
    public Class getEntityClass() {
        return User.class;
    }

    /**
     * 用户注册
     *
     * @param userDTO userDTO
     * @return SevFuncResult
     */
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
            Optional<BasePO> userPOP = userDTO.toPO();
            if (userPOP.isPresent()) {
                User user = (User) userPOP.get();
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
                    save(user, user.getAccount());
                    // 保存用户个人资料
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(commonUtil.uuid());
                    userInfo.setAccount(user.getAccount());
                    Optional<Dict> dictOptional = dictUtil.getDictByLabel(DictField.USER_SEX_UNKNOWN);
                    if (dictOptional.isPresent()) {
                        Dict dict = dictOptional.get();
                        userInfo.setSex(dict.getValue());
                    } else {
                        userInfo.setSex("2");
                    }
                    userInfo.setAccount(user.getAccount());
                    userInfo.setCreateTime(new Date());
                    userInfo.setCreateUser(user.getAccount());
                    userInfo.setUpdateTime(new Date());
                    userInfo.setUpdateUser(user.getAccount());
                    userInfoDao.insert(userInfo);
                    // 创建个人主页
                    UserHomepage userHomepage = new UserHomepage();
                    userHomepage.setId(commonUtil.uuid());
                    userHomepage.setHomepageId(commonUtil.randomCode());
                    userHomepage.setAccount(user.getAccount());
                    userHomepage.setCreateTime(new Date());
                    userHomepage.setCreateUser(user.getAccount());
                    userHomepage.setUpdateTime(new Date());
                    userHomepage.setUpdateUser(user.getAccount());
                    userHomepageDao.insert(userHomepage);
                    // 异步保存动态
                    threadUtil.execute(new UserRegisterTask(user));
                    // 返回结果
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

    /**
     * 用户登录
     *
     * @param userDTO userDTO
     * @return SevFuncResult
     */
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

    /**
     * 获取该用户的个人主页
     *
     * @param account account
     * @param homepageId homepageId
     * @return Optional<UserHomepageVO>
     */
    public Optional<UserHomepageVO> loadHomepage(@Nonnull String account, @NonNull String homepageId) {
        UserHomepageVO userHomepageVO = null;
        try {
            UserHomepageExt userHomepageExt = userHomepageDao.selectUserHomepage(homepageId);
            if (userHomepageExt == null) {
                return Optional.empty();
            }

            UserHomepage userHomepage = userHomepageExt.getUserHomepage();
            User user = userHomepageExt.getUser();
            UserInfo userInfo = userHomepageExt.getUserInfo();

            List<UserDynamic> userDynamicList = userDynamicDao.selectUserDynamicByAccount(user.getAccount());
            List<UserDynamicVO> userDynamicVOList = userDynamicList
                    .stream()
                    .map(dynamic -> {
                        UserDynamicVO userDynamicVO = null;
                        Optional<BaseVO> userDynamicVOP = dynamic.toVO();
                        if (userDynamicVOP.isPresent()) {
                            userDynamicVO = (UserDynamicVO) userDynamicVOP.get();
                            userDynamicVO.setCreateUsername(user.getUsername());
                            userDynamicVO.setHeadPortrait(user.getHeadPortrait());
                            userDynamicVO.setRelativeDate(dateUtil.relativeDateFormat(dynamic.getCreateTime()));
                            String targetId = dynamic.getTargetId();
                            if (CommonField.USER_PUBLIC_TOPIC_DYNAMIC_TYPE.equals(dynamic.getDynamicType())) {
                                Topic topic = topicDao.selectById(targetId);
                                if (topic != null) {
                                    userDynamicVO.setTargetName(topic.getTopicTitle());
                                    userDynamicVO.setDynamicContent(topic.getTopicContent());
                                }
                            } else if (CommonField.USER_PUBLIC_COMMENT_DYNAMIC_TYPE.equals(dynamic.getDynamicType())) {
                                Comment comment = commentDao.selectById(targetId);
                                String parentId = comment.getParentId();
                                if (CommonField.FIRST_LEVEL_COMMENT.equals(comment.getCommentType())) {
                                    Topic topic = topicDao.selectById(parentId);
                                    userDynamicVO.setTargetName(topic.getTopicTitle());
                                    userDynamicVO.setDynamicContent(comment.getCommentContent());
                                } else if (CommonField.SECOND_LEVEL_COMMENT.equals(comment.getCommentType())) {
                                    Comment parentComment = commentDao.selectById(parentId);
                                    userDynamicVO.setTargetName(parentComment.getCommentContent());
                                    userDynamicVO.setDynamicContent(comment.getCommentContent());
                                }
                            } else if (CommonField.USER_LIKE_TOPIC_DYNAMIC_TYPE.equals(dynamic.getDynamicType())) {
                                Topic topic = topicDao.selectById(targetId);
                                if (topic != null) {
                                    userDynamicVO.setTargetName(topic.getTopicTitle());
                                    userDynamicVO.setDynamicContent(topic.getTopicContent());
                                }
                            } else if (CommonField.USER_REGISTER_DYNAMIC_TYPE.equals(dynamic.getDynamicType())) {
                                userDynamicVO.setTargetName(user.getUsername());
                                userDynamicVO.setDynamicContent("您出生了");
                            }
                        }
                        return userDynamicVO;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Optional<BaseVO> userHomepageVOP = userHomepage.toVO();
            if (userHomepageVOP.isPresent()) {
                userHomepageVO = (UserHomepageVO) userHomepageVOP.get();
                userHomepageVO.setUsername(user.getUsername());
                userHomepageVO.setHeadPortrait(user.getHeadPortrait());
                if (!StringUtils.isEmpty(account)) {
                    userHomepageVO.setSelf(account.equals(user.getAccount()));
                }
                userHomepageVO.setUserInfoVO((UserInfoVO) userInfo.toVO().orElse(null));
                userHomepageVO.setUserDynamicVOList(userDynamicVOList);
            }
        } catch (Exception e) {
            log.error("UserSev loadHomepage errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(userHomepageVO);
    }

    /**
     * 上传头像
     *
     * @param file file
     * @param account account
     * @return SevFuncResult
     */
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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            sevFuncResult = new SevFuncResult(true, "未知错误", ServerStatusCode.UNKNOWN);
        }
        return sevFuncResult;
    }

    /**
     * 根据 account 获取 UserVO
     * @param account account
     * @return Optional<UserVO>
     */
    public Optional<UserVO> getUserVOByAccount(String account) {
        UserVO userVO = null;
        try {
            User user = selectById(account);
            if (user != null) {
                Optional<BaseVO> baseVOptional = user.toVO();
                if (baseVOptional.isPresent()) {
                    userVO = (UserVO) baseVOptional.get();
                    QueryWrapper<UserHomepage> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("account", account);
                    UserHomepage userHomepage = userHomepageDao.selectOne(queryWrapper);
                    if (userHomepage != null) {
                        userVO.setHomepageId(userHomepage.getHomepageId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("UserSev getUserVOByAccount errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(userVO);
    }
}