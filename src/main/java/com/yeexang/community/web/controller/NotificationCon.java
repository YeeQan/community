package com.yeexang.community.web.controller;

import com.yeexang.community.common.constant.DictField;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.DictUtil;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.po.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/9/28
 */
@Slf4j
@RestController
@RequestMapping("notification")
@Api(tags = "通知管理 Controller")
public class NotificationCon {

    @Autowired
    private DictUtil dictUtil;

    @PostMapping("announcement")
    @ApiOperation(value = "获取公告")
    public ResponseEntity<String> announcement() {
        List<Dict> dictList = dictUtil.getDictByType(DictField.COMMUNITY_ANNOUNCEMENT);
        if (dictList.isEmpty()) {
            return new ResponseEntity<>("快来畅所欲言吧~");
        }
        // 根据更新时间做降序排序，取最新的公告
        dictList.sort((dict1, dict2) -> {
            long dict1UpdateTime = dict1.getUpdateTime().getTime();
            long dict2UpdateTime = dict2.getUpdateTime().getTime();
            return Long.compare(dict2UpdateTime, dict1UpdateTime);
        });
        String announcement = dictList.get(0).getValue();
        return new ResponseEntity<>(announcement);
    }
}
