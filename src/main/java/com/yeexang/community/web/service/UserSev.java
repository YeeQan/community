package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.User;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/23
 */
public interface UserSev {

    List<User> getUser(UserDTO userDTO);

    List<User> register(UserDTO userDTO);
}
