package com.yeexang.community.web.controller;

import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.UserSev;
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
import java.util.stream.Collectors;

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

    @Autowired
    private UserSev userSev;

    @PostMapping("list")
    @ApiOperation(value = "获取评论列表")
    public ResponseEntity<CommentDTO> list(@RequestBody RequestEntity<CommentDTO> requestEntity) {

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            commentDTO = new CommentDTO();
        } else {
            commentDTO = data.get(0);
        }

        List<Comment> commentList = commentSev.getCommentList(commentDTO);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<CommentDTO> commentDTOList = commentList.stream()
                .map(comment -> {
                    CommentDTO dto = null;
                    Optional<BaseDTO> optional = comment.toDTO();
                    if (optional.isPresent()) {
                        dto = (CommentDTO) optional.get();
                        UserDTO param = new UserDTO();
                        param.setAccount(dto.getCreateUser());
                        List<User> userList = userSev.getUser(param);
                        if (!userList.isEmpty()) {
                            User user = userList.get(0);
                            dto.setCreateUsername(user.getUsername());
                        }
                    }
                    return dto;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOList);
    }

    @PostMapping("publish")
    @ApiOperation(value = "发布评论")
    public ResponseEntity<CommentDTO> publish(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

        String account = request.getAttribute("account").toString();

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
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

        List<Comment> commentList = commentSev.publish(commentDTO, account);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<CommentDTO> commentDTOList = commentList.stream()
                .map(comment -> {
                    CommentDTO dto = null;
                    Optional<BaseDTO> optional = comment.toDTO();
                    if (optional.isPresent()) {
                        dto = (CommentDTO) optional.get();
                        UserDTO param = new UserDTO();
                        param.setAccount(dto.getCreateUser());
                        List<User> userList = userSev.getUser(param);
                        if (!userList.isEmpty()) {
                            User user = userList.get(0);
                            dto.setCreateUsername(user.getUsername());
                        }
                    }
                    return dto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList);
    }

    @PostMapping("like")
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
                    Optional<BaseDTO> optional = comment.toDTO();
                    if (optional.isPresent()) {
                        dto = (CommentDTO) optional.get();
                        UserDTO param = new UserDTO();
                        param.setAccount(dto.getCreateUser());
                        List<User> userList = userSev.getUser(param);
                        if (!userList.isEmpty()) {
                            User user = userList.get(0);
                            dto.setCreateUsername(user.getUsername());
                        }
                    }
                    return dto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList);
    }
}
