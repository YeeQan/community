package com.yeexang.community.web.controller;

import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.CookieUtil;
import com.yeexang.community.common.util.JwtUtil;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserCon {

    @Autowired
    private UserSev userSev;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@RequestBody RequestEntity<UserDTO> request, HttpServletResponse response) {

        log.info("UserCon register start --------------------------------");

        UserDTO userDTO = request.getData().get(0);

        if (StringUtils.isEmpty(userDTO.getAccount())) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_EMPTY);
        } else if (!userDTO.getAccount().matches("[a-zA-Z0-9_]{1,12}")) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_FORMAT_ERROR);
        } else if (StringUtils.isEmpty(userDTO.getUsername())) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_EMPTY);
        } else if (userDTO.getUsername().length() > 12 ||
                !userDTO.getUsername().matches("[\\u4E00-\\u9FA5A-Za-z0-9_]+$")) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_FORMAT_ERROR);
        } else if (StringUtils.isEmpty(userDTO.getPassword())) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_EMPTY);
        } else if (!userDTO.getPassword().matches("[a-zA-Z0-9]{1,16}")) {
            return new ResponseEntity<>(ServerStatusCode.PASSWORD_FORMAT_ERROR);
        }

        if (!userSev.getUser(new UserDTO(userDTO.getAccount(), null, null)).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.ACCOUNT_EXIST);
        }

        if (!userSev.getUser(new UserDTO(null, userDTO.getUsername(), null)).isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.USERNAME_EXIST);
        }

        List<User> userList = userSev.register(userDTO);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
        }

        List<UserDTO> userDTOList = userList.stream()
                .map(user -> (UserDTO) user.toDTO()).collect(Collectors.toList());

        Map<String, String> payloadMap = new HashMap<>(2);
        payloadMap.put("account", userDTO.getAccount());
        String token = jwtUtil.getToken(payloadMap);

        Cookie cookie = cookieUtil.getCookie("token", token, 86400 * 7);
        response.addCookie(cookie);

        log.info("UserCon register end --------------------------------");

        return new ResponseEntity<>(userDTOList);
    }
}
