package com.yeexang.community.web.service.impl;

import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                userDao.insert(user);
                userList.addAll(userDao.select(user));
            }
        } catch (Exception e) {
            log.error("UserSev register errorMsg: {}", e.getMessage());
        }
        return userList;
    }
}
