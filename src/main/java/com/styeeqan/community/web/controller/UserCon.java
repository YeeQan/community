package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.annotation.group.user.Login;
import com.styeeqan.community.common.annotation.group.user.Register;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.dto.UserDTO;
import com.styeeqan.community.pojo.vo.UserVO;
import com.styeeqan.community.web.service.UserSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("user")
@Api(tags = "用户管理Controller")
public class UserCon {

    @Autowired
    private UserSev userSev;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<?> register(@RequestBody @Validated(Register.class) UserDTO userDTO, HttpServletResponse response) throws Exception {
        userSev.register(userDTO.getAccount(), userDTO.getUsername(), userDTO.getPassword(), response);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public ResponseEntity<?> login(@RequestBody @Validated(Login.class) UserDTO userDTO, HttpServletResponse response) {
        userSev.login(userDTO.getAccount(), userDTO.getPassword(), response);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("loginInfo")
    @ApiOperation(value = "登录状态信息")
    public ResponseEntity<UserVO> loginInfo(HttpServletRequest request) {
        UserVO userVO = userSev.getLoginInfo(request.getAttribute(CommonField.ACCOUNT).toString());
        return new ResponseEntity<>(userVO);
    }

    @PostMapping("logout")
    @ApiOperation(value = "用户登出")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        userSev.logout(response);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }
}