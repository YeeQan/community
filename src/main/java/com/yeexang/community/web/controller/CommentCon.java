package com.yeexang.community.web.controller;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.UserSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@RestController
@RequestMapping("comment")
public class CommentCon {

    @Autowired
    private CommentSev commentSev;

    @Autowired
    private UserSev userSev;

    @PostMapping("list")
    public ResponseEntity<CommentDTO> list(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

        log.info("CommentCon list start --------------------------------");

        CommentDTO commentDTO;
        List<CommentDTO> data = requestEntity.getData();
        if (data == null || data.isEmpty()) {
            commentDTO = new CommentDTO();
        } else {
            commentDTO = data.get(0);
        }

        List<Comment> commentList = commentSev.getCommentList(commentDTO);
        if (commentList == null || commentList.isEmpty()) {
            return new ResponseEntity<>(ServerStatusCode.DATA_NOT_FOUND);
        }

        List<CommentDTO> commentDTOList = commentList.stream()
                .map(comment -> (CommentDTO) comment.toDTO()).collect(Collectors.toList());

        log.info("CommentCon list end --------------------------------");

        return new ResponseEntity<>(commentDTOList);
    }

    @PostMapping("publish")
    public ResponseEntity<CommentDTO> publish(@RequestBody RequestEntity<CommentDTO> requestEntity, HttpServletRequest request) {

        log.info("CommentCon publish start --------------------------------");

        String account = request.getAttribute("account").toString();
        CommentDTO commentDTO = requestEntity.getData().get(0);

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
                    CommentDTO dto = (CommentDTO) comment.toDTO();
                    UserDTO param = new UserDTO();
                    param.setAccount(dto.getCreateUser());
                    User user = userSev.getUser(param).get(0);
                    dto.setCreateUsername(user.getUsername());
                    return dto;
                }).collect(Collectors.toList());

        log.info("CommentCon publish end --------------------------------");

        return new ResponseEntity<>(commentDTOList);
    }
}
