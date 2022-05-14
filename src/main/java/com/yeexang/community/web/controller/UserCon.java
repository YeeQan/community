package com.yeexang.community.web.controller;

import com.yeexang.community.common.annotation.RateLimiterAnnotation;
import com.yeexang.community.common.annotation.ReqParamVerify;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.http.response.SevFuncResult;
import com.yeexang.community.common.util.CookieUtil;
import com.yeexang.community.common.util.JwtUtil;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.dto.UserHomepageDTO;
import com.yeexang.community.pojo.vo.UserHomepageVO;
import com.yeexang.community.pojo.vo.UserVO;
import com.yeexang.community.web.service.UserSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @ReqParamVerify
    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<?> register(@RequestBody RequestEntity<UserDTO> requestEntity, HttpServletResponse response) {

        UserDTO userDTO = requestEntity.getData().get(0);

        // 格式校验
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

        // 注册
        SevFuncResult result = userSev.register(userDTO);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getStatusCode());
        }

        // 设置 token
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

        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @ReqParamVerify
    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public ResponseEntity<?> login(@RequestBody RequestEntity<UserDTO> requestEntity, HttpServletResponse response) {

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

        SevFuncResult result = userSev.login(userDTO);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getStatusCode());
        }

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

        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    @RateLimiterAnnotation(permitsPerSecond = 1.0)
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 设置 Cookie 失效
        Optional<Cookie> optionalCookie = cookieUtil.getCookie(CommonField.TOKEN, null, 0);
        if (optionalCookie.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
        }
        Cookie cookie = optionalCookie.get();
        response.addCookie(cookie);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("loginInfo")
    @ApiOperation(value = "登录状态信息")
    public ResponseEntity<UserVO> loginInfo(HttpServletRequest request) {

        String account = request.getAttribute(CommonField.ACCOUNT).toString();

        // 获取用户信息
        Optional<UserVO> optional = userSev.getUserVOByAccount(account);

        if (optional.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        return new ResponseEntity<>(optional.get());
    }

    @ReqParamVerify
    @PostMapping("person")
    @ApiOperation(value = "用户个人主页")
    @RateLimiterAnnotation(permitsPerSecond = 1.0)
    public ResponseEntity<UserHomepageVO> homepage(@RequestBody RequestEntity<UserHomepageDTO> requestEntity, HttpServletRequest request) {

        UserHomepageDTO userHomepageDTO = requestEntity.getData().get(0);

        String account = request.getAttribute(CommonField.ACCOUNT) == null ? null : request.getAttribute(CommonField.ACCOUNT).toString();

        // 加载该用户的个人主页
        Optional<UserHomepageVO> optional = userSev.loadHomepage(account, userHomepageDTO.getHomepageId());

        if (optional.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        return new ResponseEntity<>(optional.get());
    }

    @PostMapping("upload/headPortrait")
    @ApiOperation(value = "上传用户头像")
    @RateLimiterAnnotation(permitsPerSecond = 1.0)
    public ResponseEntity<?> uploadHeadPortrait(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String account = request.getAttribute(CommonField.ACCOUNT).toString();

        SevFuncResult result = userSev.uploadHeadPortrait(file, account);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getStatusCode());
        }

        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }
}