package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.common.util.ThreadUtil;
import com.styeeqan.community.pojo.vo.UserDynamicVO;
import com.styeeqan.community.web.service.UserDynamicSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user/dynamic")
@Api(tags = "用户动态管理Controller")
public class UserDynamicCon {

    @Autowired
    private UserDynamicSev userDynamicSev;

    @PostMapping("list")
    @ApiOperation(value = "用户动态列表")
    public ResponseEntity<?> list(HttpServletRequest request) {
        List<UserDynamicVO> list = userDynamicSev.getDynamicList(request.getAttribute(CommonField.ACCOUNT).toString());
        return new ResponseEntity<>(list);
    }
}