package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/2
 */
public interface CommentSev {

    /**
     * 获取评论列表
     * @param commentDTO commentDTO
     * @return List<Comment>
     */
    List<Comment> getCommentList(CommentDTO commentDTO);

    /**
     * 发布评论
     * @param commentDTO commentDTO
     * @param account account
     * @return List<Comment>
     */
    List<Comment> publish(CommentDTO commentDTO, String account);

    /**
     * 点赞
     * @param commentDTO commentDTO
     * @param account account
     * @return List<Comment>
     */
    List<Comment> like(CommentDTO commentDTO, String account);
}
