package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.User;

import java.util.List;

/**
 * 用户管理 Service
 *
 * @author yeeq
 * @date 2021/7/23
 */
public interface UserSev {

    /**
     * 获取用户信息
     * @param userDTO userDTO
     * @return List<User>
     */
    List<User> getUser(UserDTO userDTO);

    /**
     * 用户注册
     *
     * @param userDTO userDTO
     * @return List<User>
     */
    List<User> register(UserDTO userDTO);
}
