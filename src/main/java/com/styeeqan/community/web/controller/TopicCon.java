package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.annotation.RateLimiterAnnotation;
import com.styeeqan.community.common.annotation.group.topic.Publish;
import com.styeeqan.community.common.annotation.group.topic.Visit;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.dto.TopicDTO;
import com.styeeqan.community.pojo.vo.PageVO;
import com.styeeqan.community.pojo.vo.TopicVO;
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
    public ResponseEntity<PageVO> page(@RequestBody TopicDTO topicDTO) {
        PageVO<TopicVO> pageVO = topicSev.getPage(topicDTO.getPageNum(), topicDTO.getPageSize());
        return new ResponseEntity<>(pageVO);
    }

    @PostMapping("visit")
    @ApiOperation(value = "访问帖子")
    public ResponseEntity<TopicVO> visit(@RequestBody @Validated(Visit.class) TopicDTO topicDTO) {
        TopicVO topicVO = topicSev.visit(topicDTO.getTopicId());
        return new ResponseEntity<>(topicVO);
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布帖子")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<TopicVO> publish(@RequestBody @Validated(Publish.class) TopicDTO topicDTO, HttpServletRequest request) {
        TopicVO topicVO = topicSev.publish(
                topicDTO.getTopicId(), topicDTO.getTopicTitle(), topicDTO.getTopicContent(),
                request.getAttribute(CommonField.ACCOUNT).toString()
        );
        return new ResponseEntity<>(topicVO);
    }
}
