package com.styeeqan.community.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@Api(tags = "页面跳转路由Controller")
public class ViewCon {

    @GetMapping(value = {"/", "/index"})
    @ApiOperation(value = "首页")
    public String index() {
        return "index";
    }

    @GetMapping("/publish")
    @ApiOperation(value = "发帖页")
    public String publish() {
        return "publish";
    }

    @GetMapping("/topic/view/**")
    @ApiOperation(value = "帖子详情页")
    public String topic() {
        return "topic";
    }

    @GetMapping("/common/header-logined")
    @ApiOperation(value = "顶部栏已登录")
    public String headerLogined() {
        return "common/header-logined";
    }

    @GetMapping("/common/header-non-logined")
    @ApiOperation(value = "顶部栏未登录")
    public String headerNonLogined() {
        return "common/header-non-logined";
    }

    @GetMapping("/common/footer")
    @ApiOperation(value = "页脚")
    public String footer() {
        return "common/footer";
    }

    @GetMapping("/error")
    @ApiOperation(value = "错误页面")
    public String error() {
        return "error";
    }

    @GetMapping("/u/**")
    @ApiOperation(value = "错误页面")
    public String homepage() {
        return "homepage";
    }
}
