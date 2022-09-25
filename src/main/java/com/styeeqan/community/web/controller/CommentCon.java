package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.annotation.group.comment.Publish;
import com.styeeqan.community.common.annotation.group.comment.Reply;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.pojo.dto.CommentDTO;
import com.styeeqan.community.pojo.vo.CommentVO;
import com.styeeqan.community.web.service.CommentSev;
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

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@RestController
@RequestMapping("comment")
@Api(tags = "评论管理Controller")
public class CommentCon {

    @Autowired
    private CommentSev commentSev;

    @PostMapping("publish")
    @ApiOperation(value = "发布评论")
    public ResponseEntity<?> publish(@RequestBody @Validated(Publish.class) CommentDTO commentDTO, HttpServletRequest request) {
        CommentVO commentVO = commentSev.publish(commentDTO.getParentId(), commentDTO.getCommentContent(),
                request.getAttribute(CommonField.ACCOUNT).toString());
        return new ResponseEntity<>(commentVO);
    }
}
