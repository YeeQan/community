package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.po.Comment;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/2
 */
public interface CommentSev {

    List<Comment> getCommentList(CommentDTO commentDTO);

    List<Comment> publish(CommentDTO commentDTO, String account);
}
