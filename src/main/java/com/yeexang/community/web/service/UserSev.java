package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Topic;
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
     * 保存用户信息
     * @param userDTO userDTO
     */
    void saveUser(UserDTO userDTO);

    /**
     * 用户注册
     *
     * @param userDTO userDTO
     * @return List<User>
     */
    List<User> register(UserDTO userDTO);

    /**
     * 获取该用户发布的帖子
     * @param account account
     * @return List<Topic>
     */
    List<Topic> getUserTopicList(String account);
}