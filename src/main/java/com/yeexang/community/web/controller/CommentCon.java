package com.yeexang.community.web.controller;

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
import java.util.Optional;

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
    public ResponseEntity<CommentVO> firstCommentList(@RequestBody RequestEntity<CommentDTO> requestEntity) {

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty() || data.get(0) == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        List<CommentVO> commentVOList = commentSev.getFirstLevelComment(commentDTO);

        return new ResponseEntity<>(commentVOList);
    }

    @PostMapping("second/list")
    @ApiOperation(value = "获取二级评论列表")
    public ResponseEntity<CommentVO> secondCommentList(@RequestBody RequestEntity<CommentDTO> requestEntity) {

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty() || data.get(0) == null) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        List<CommentVO> commentVOList = commentSev.getSecondLevelComment(commentDTO);

        return new ResponseEntity<>(commentVOList);
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布评论")
    public ResponseEntity<CommentVO> publish(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

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

        commentDTO.setCreateUser(account);
        commentDTO.setUpdateUser(account);
        Optional<CommentVO> commentVOP = commentSev.publish(commentDTO);

        if (commentVOP.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.RESPONSE_DATA_EMPTY);
        }

        return new ResponseEntity<>(commentVOP.get());
    }

    /*@PostMapping("like")
    @ApiOperation(value = "点赞评论")
    public ResponseEntity<CommentDTO> like(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

        String account = request.getAttribute("account").toString();

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
        } else {
            commentDTO = data.get(0);
        }

        // 点赞
        List<Comment> commentList = commentSev.like(commentDTO, account);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<CommentDTO> commentDTOList = commentList.stream()
                .map(comment -> {
                    CommentDTO dto = null;
                    Optional<BaseDTO> optional = comment.toVO();
                    if (optional.isPresent()) {
                        dto = (CommentDTO) optional.get();
                        UserDTO param = new UserDTO();
                        param.setAccount(dto.getCreateUser());
                        List<User> userList = userSev.selectUser(param);
                        if (!userList.isEmpty()) {
                            User user = userList.get(0);
                            dto.setCreateUsername(user.getUsername());
                            dto.setHeadPortrait(user.getHeadPortrait());
                        }
                    }
                    return dto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList);
    }*/
}
