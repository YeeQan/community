package com.yeexang.community.web.service.impl;

import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@Service
public class UserSevImpl implements UserSev {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUser(UserDTO userDTO) {
        User user = (User) userDTO.toPO();
        List<User> userList = new ArrayList<>();
        try {
            userList.addAll(userDao.select(user));
        } catch (Exception e) {
            log.error("UserSev getUser errorMsg: {}", e.getMessage());
        }
        return userList;
    }

    @Override
    public List<User> register(UserDTO userDTO) {
        User user = (User) userDTO.toPO();
        List<User> userList = new ArrayList<>();
        try {
            // 避免重复注册
            synchronized (this) {
                user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                user.setExp(0);
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
        } catch (Exception e) {
            log.error("UserSev register errorMsg: {}", e.getMessage());
        }
        return userList;
    }
}
