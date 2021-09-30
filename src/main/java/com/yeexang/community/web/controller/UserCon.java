package com.yeexang.community.web.controller;

import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.CookieUtil;
import com.yeexang.community.common.util.DictUtil;
import com.yeexang.community.common.util.JwtUtil;
import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.UserSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@RestController
@RequestMapping("user")
@Api(tags = "用户管理 Controller")
public class UserCon {

    @Autowired
    private UserSev userSev;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<UserDTO> register(@RequestBody RequestEntity<UserDTO> requestEntity, HttpServletResponse response) {

        UserDTO userDTO = requestEntity.getData().get(0);

        // 格式校验
        if (userDTO == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        }
        if (StringUtils.isEmpty(userDTO.getAccount())) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_EMPTY);
        }
        if (!userDTO.getAccount().matches(CommonField.ACCOUNT_FORMAT_REGULAR)) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(userDTO.getUsername())) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_EMPTY);
        }
        if (userDTO.getUsername().length() > 12 ||
                !userDTO.getUsername().matches(CommonField.USERNAME_FORMAT_REGULAR)) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(userDTO.getPassword())) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_EMPTY);
        }
        if (!userDTO.getPassword().matches(CommonField.PASSWORD_FORMAT_REGULAR)) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_FORMAT_ERROR);
        }

        // 账号已存在
        if (!userSev.getUser(new UserDTO(userDTO.getAccount(), null, null)).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_EXIST);
        }

        // 昵称已存在
        if (!userSev.getUser(new UserDTO(null, userDTO.getUsername(), null)).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_EXIST);
        }

        // 注册
        List<User> userList = userSev.register(userDTO);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        List<UserDTO> userDTOList = userList.stream()
                .map(user -> {
                    UserDTO dto = null;
                    Optional<BaseDTO> optional = user.toDTO();
                    if (optional.isPresent()) {
                        dto = (UserDTO) optional.get();
                    }
                    return dto;
                }).collect(Collectors.toList());

        Map<String, String> payloadMap = new HashMap<>(2);
        payloadMap.put(CommonField.ACCOUNT, userDTO.getAccount());
        Optional<String> optionalToken = jwtUtil.getToken(payloadMap);
        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            Optional<Cookie> optionalCookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 7);
            if (optionalCookie.isPresent()) {
                Cookie cookie = optionalCookie.get();
                response.addCookie(cookie);
            }
        }

        return new ResponseEntity<>(userDTOList);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public ResponseEntity<UserDTO> login(@RequestBody RequestEntity<UserDTO> requestEntity, HttpServletResponse response) {

        UserDTO userDTO = requestEntity.getData().get(0);

        // 格式校验
        if (userDTO == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        }
        if (StringUtils.isEmpty(userDTO.getAccount())) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_EMPTY);
        }
        if (!userDTO.getAccount().matches(CommonField.ACCOUNT_FORMAT_REGULAR)) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(userDTO.getPassword())) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_EMPTY);
        }
        if (!userDTO.getPassword().matches(CommonField.PASSWORD_FORMAT_REGULAR)) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_FORMAT_ERROR);
        }

        // 获取用户信息
        List<User> userList = userSev.getUser(userDTO);
        // 用户不存在
        if (userList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_NOT_EXIST);
        }

        List<UserDTO> userDTOList = userList.stream()
                .map(user -> {
                    UserDTO dto = null;
                    Optional<BaseDTO> optional = user.toDTO();
                    if (optional.isPresent()) {
                        dto = (UserDTO) optional.get();
                    }
                    return dto;
                }).collect(Collectors.toList());

        Map<String, String> payloadMap = new HashMap<>(2);
        payloadMap.put(CommonField.ACCOUNT, userDTO.getAccount());
        Optional<String> optionalToken = jwtUtil.getToken(payloadMap);
        if (optionalToken.isPresent()) {
            String token = optionalToken.get();
            Optional<Cookie> optionalCookie = cookieUtil.getCookie(CommonField.TOKEN, token, 86400 * 7);
            if (optionalCookie.isPresent()) {
                Cookie cookie = optionalCookie.get();
                response.addCookie(cookie);
            }
        }
        return new ResponseEntity<>(userDTOList);
    }

    @PostMapping("loginInfo")
    @ApiOperation(value = "登录状态信息")
    public ResponseEntity<UserDTO> loginInfo(HttpServletRequest request) {

        String account = request.getAttribute(CommonField.ACCOUNT).toString();
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(account);

        // 获取用户信息
        List<User> userList = userSev.getUser(userDTO);
        // 用户不存在
        if (userList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_NOT_EXIST);
        }

        List<UserDTO> userDTOList = userList.stream()
                .map(user -> {
                    UserDTO dto = null;
                    Optional<BaseDTO> optional = user.toDTO();
                    if (optional.isPresent()) {
                        dto = (UserDTO) optional.get();
                    }
                    return dto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(userDTOList);
    }
}
