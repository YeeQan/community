package com.yeexang.community.web.controller;

import com.yeexang.community.common.constant.NotificationField;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.NotificationSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author yeeq
 * @date 2022/1/30
 */
@Slf4j
@Controller
@Api(tags = "页面跳转路由 Controller")
public class ViewCon {

    @Autowired
    private NotificationSev notificationSev;

    @Autowired
    private CommentSev commentSev;

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

    @GetMapping("/edit/**")
    @ApiOperation(value = "编辑页")
    public String edit() {
        return "edit";
    }

    @GetMapping("/topic/view/**")
    @ApiOperation(value = "帖子详情页")
    public String topic() {
        return "topic";
    }

    @GetMapping("/homepage/**")
    @ApiOperation(value = "个人主页")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/notification/**")
    @ApiOperation(value = "评论通知")
    public String notification() {
        return "notification";
    }

    @GetMapping("/readNotifi/{notificationId}")
    @ApiOperation(value = "评论通知")
    public String readNotifi(@PathVariable String notificationId) {
        if (StringUtils.isEmpty(notificationId)) {
            return "redirect:/error";
        }
        String url = null;
        Notification notification = notificationSev.selectById(notificationId);
        if (notification != null) {
            String type = notification.getNotificationType();
            if (NotificationField.TOPIC_VALUE.equals(type) || NotificationField.TOPIC_LIKE_VALUE.equals(type)) {
                url = "redirect:/topic/view/" + notification.getOuterId();
            } else if (NotificationField.COMMENT_VALUE.equals(type)) {
                Comment comment = commentSev.selectById(notification.getOuterId());
                url = "redirect:/topic/view/" + comment.getParentId();
            }
        }
        if (StringUtils.isEmpty(url)) {
            url = "redirect:/error";
        } else {
            notificationSev.read(notificationId);
        }
        return url;
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
}
