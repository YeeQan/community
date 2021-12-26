package com.yeexang.community.web.controller;

import com.yeexang.community.common.annotation.RateLimiterAnnotation;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.vo.CommentVO;
import com.yeexang.community.web.service.CommentSev;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@RestController
@RequestMapping("comment")
@Api(tags = "评论管理 Controller")
public class CommentCon {

    @Autowired
    private CommentSev commentSev;

    @PostMapping("first/list")
    @ApiOperation(value = "获取一级评论列表")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<CommentVO> firstCommentList(@RequestBody RequestEntity<CommentDTO> requestEntity) {

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()
                || data.get(0) == null || StringUtils.isEmpty(data.get(0).getParentId())) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        List<CommentVO> commentVOList = commentSev.getFirstLevelComment(commentDTO);

        return new ResponseEntity<>(commentVOList);
    }

    @PostMapping("second/list")
    @ApiOperation(value = "获取二级评论列表")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<CommentVO> secondCommentList(@RequestBody RequestEntity<CommentDTO> requestEntity) {

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()
                || data.get(0) == null || StringUtils.isEmpty(data.get(0).getParentId())) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        List<CommentVO> commentVOList = commentSev.getSecondLevelComment(commentDTO);

        return new ResponseEntity<>(commentVOList);
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布评论")
    @RateLimiterAnnotation(permitsPerSecond = 2.0)
    public ResponseEntity<?> publish(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

        String account = request.getAttribute(CommonField.ACCOUNT).toString();

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty() || data.get(0) == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        // 参数校验
        if (StringUtils.isEmpty(commentDTO.getCommentContent())) {
            return new ResponseEntity<>(ServerStatusCode.COMMENT_CONTENT_EMPTY);
        }
        if (commentDTO.getCommentContent().length() > 500) {
            return new ResponseEntity<>(ServerStatusCode.COMMENT_CONTENT_TOO_LONG);
        }

        commentSev.publish(commentDTO, account);

        return new ResponseEntity<>(ServerStatusCode.SUCCESS);
    }
}
