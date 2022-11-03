package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.annotation.group.user.UserHomepage;
import com.styeeqan.community.common.annotation.group.user.UserInfoSave;
import com.styeeqan.community.common.annotation.group.user.UserLogin;
import com.styeeqan.community.common.annotation.group.user.UserRegister;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.dto.UserDTO;
import com.styeeqan.community.pojo.vo.UserHomepageVO;
import com.styeeqan.community.pojo.vo.UserVO;
import com.styeeqan.community.web.service.UserSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("user")
@Api(tags = "用户管理Controller")
public class UserCon {

    @Autowired
    private UserSev userSev;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<?> register(@RequestBody @Validated(UserRegister.class) UserDTO userDTO, HttpServletResponse response) throws Exception {
        userSev.register(userDTO.getAccount(), userDTO.getUsername(), userDTO.getPassword(), response);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public ResponseEntity<?> login(@RequestBody @Validated(UserLogin.class) UserDTO userDTO, HttpServletResponse response) {
        userSev.login(userDTO.getAccount(), userDTO.getPassword(), response);
        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }

    @PostMapping("loginFlag")
    @ApiOperation(value = "用户登录状态")
    public ResponseEntity<?> loginFlag(HttpServletRequest request) {
        ResponseEntity<String> response;
        Object attribute = request.getAttribute(CommonField.ACCOUNT);
        if (attribute == null) {
            response = new ResponseEntity<>(ServerStatusCode.UNAUTHORIZED);
            response.setData("/community/common/header-non-logined");
        } else {
            response = new ResponseEntity<>(ServerStatusCode.SUCCESS);
            response.setData("/community/common/header-logined");
        }
        return response;
    }

    @PostMapping("loginInfo")
    @ApiOperation(value = "用户登录信息")
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

    @PostMapping("homepage")
    @ApiOperation(value = "用户主页")
    public ResponseEntity<?> homepage(@RequestBody @Validated(UserHomepage.class) UserDTO userDTO, HttpServletRequest request) {
        Object attribute = request.getAttribute(CommonField.ACCOUNT);
        UserHomepageVO userHomepageVO = userSev.loadHomepage(userDTO.getHomepageId(), attribute == null ? null : attribute.toString());
        return new ResponseEntity<>(userHomepageVO);
    }

    @PostMapping("userInfo/get")
    @ApiOperation(value = "获取个人资料")
    public ResponseEntity<UserVO> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<>(userSev.getUserInfo(request.getAttribute(CommonField.ACCOUNT).toString()));
    }

    @PostMapping("userInfo/save")
    @ApiOperation(value = "保存个人资料")
    public ResponseEntity<UserVO> saveUserInfo(@RequestBody @Validated(UserInfoSave.class) UserDTO userDTO, HttpServletRequest request) {
        userDTO.setAccount(request.getAttribute(CommonField.ACCOUNT).toString());
        return new ResponseEntity<>(userSev.saveUserInfo(userDTO));
    }

    @SneakyThrows
    @PostMapping("headPortrait/upload")
    @ApiOperation(value = "上传用户头像")
    public ResponseEntity<String> headPortrait(HttpServletRequest request, @RequestParam(name = "file") MultipartFile multipartFile) {
        return new ResponseEntity<>(userSev.uploadHeadPortrait(request.getAttribute(CommonField.ACCOUNT).toString(), multipartFile));
    }
}