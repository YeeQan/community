package com.yeexang.community.web.service.impl;

import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSevImpl implements UserSev {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<User> getUser(UserDTO userDTO) {
        List<User> userList = new ArrayList<>();
        try {
            Optional<BasePO> optional = userDTO.toPO();
            if (optional.isPresent()) {
                User user = (User) optional.get();
                List<User> userDBList = userDao.select(user);
                userList.addAll(userDBList);
            }
        } catch (Exception e) {
            log.error("UserSev getUser errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return userList;
    }

    @Override
    public List<User> register(UserDTO userDTO) {
        List<User> userList = new ArrayList<>();
        try {
            Optional<BasePO> optional = userDTO.toPO();
            if (optional.isPresent()) {
                User user = (User) optional.get();
                // 避免重复注册
                synchronized (this) {
                    user.setId(commonUtil.uuid());
                    // 对用户密码做 MD5 加密
                    String pwd = userDTO.getPassword();
                    String pwdMD5 = DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
                    user.setPassword(pwdMD5);
                    user.setCreateTime(new Date());
                    user.setCreateUser(user.getAccount());
                    user.setUpdateTime(new Date());
                    user.setUpdateUser(user.getAccount());
                    user.setDelFlag(false);
                    userDao.insert(user);

                    User userParam = new User();
                    userParam.setAccount(user.getAccount());
                    List<User> userDBList = userDao.select(userParam);
                    userList.addAll(userDBList);
                }
            }
        } catch (Exception e) {
            log.error("UserSev register errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return userList;
    }

    @Override
    public List<Topic> getUserTopicList(String account) {
        List<Topic> topicList = new ArrayList<>();
        try {
            List<Topic> topicDBList = topicDao.selectByUserAccount(account);
            topicList.addAll(topicDBList);
        } catch (Exception e) {
            log.error("UserSev getUser errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }
}
