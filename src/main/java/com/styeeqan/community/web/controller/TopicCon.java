package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.annotation.RateLimiterAnnotation;
import com.styeeqan.community.common.annotation.group.topic.TopicPublish;
import com.styeeqan.community.common.annotation.group.topic.TopicVisit;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.dto.TopicDto;
import com.styeeqan.community.pojo.vo.PageVo;
import com.styeeqan.community.pojo.vo.TopicVo;
import com.styeeqan.community.web.service.TopicSev;
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

@Slf4j
@RestController
@RequestMapping("topic")
@Api(tags = "帖子管理Controller")
public class TopicCon {

    @Autowired
    private TopicSev topicSev;

    @PostMapping("page")
    @ApiOperation(value = "获取帖子分页")
    public ResponseEntity<PageVo> page(@RequestBody TopicDto topicDTO) {
        PageVo<TopicVo> pageVO = topicSev.getPage(topicDTO.getPageNum(), topicDTO.getPageSize(), topicDTO.getSortOrder());
        return new ResponseEntity<>(pageVO);
    }

    @PostMapping("visit")
    @ApiOperation(value = "访问帖子")
    public ResponseEntity<TopicVo> visit(@RequestBody @Validated(TopicVisit.class) TopicDto topicDTO) {
        TopicVo topicVO = topicSev.visit(topicDTO.getTopicId(), topicDTO.getCommentPageNum(), topicDTO.getCommentPageSize());
        return new ResponseEntity<>(topicVO);
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布帖子")
    public ResponseEntity<TopicVo> publish(@RequestBody @Validated(TopicPublish.class) TopicDto topicDTO, HttpServletRequest request) {
        TopicVo topicVO = topicSev.publish(
                topicDTO.getTopicId(), topicDTO.getTopicTitle(), topicDTO.getTopicContent(),
                topicDTO.getTags(), request.getAttribute(CommonField.ACCOUNT).toString());
        return new ResponseEntity<>(topicVO);
    }
}
