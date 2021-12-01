package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.vo.CommentVO;

import java.util.List;
import java.util.Optional;

/**
 * 评论管理 Service
 *
 * @author yeeq
 * @date 2021/8/2
 */
public interface CommentSev {

    /**
     * 获取一级评论
     *
     * @param commentDTO commentDTO
     * @return List<CommentVO>
     */
    List<CommentVO> getFirstLevelComment(CommentDTO commentDTO);

    /**
     * 获取二级评论
     *
     * @param commentDTO commentDTO
     * @return List<CommentVO>
     */
    List<CommentVO> getSecondLevelComment(CommentDTO commentDTO);

    /**
     * 发布评论
     *
     * @param commentDTO commentDTO
     * @return Optional<Comment>
     */
    Optional<CommentVO> publish(CommentDTO commentDTO);

    /**
     * 点赞
     * @param commentDTO commentDTO
     * @param account account
     * @return List<Comment>
     */
    /*List<Comment> like(CommentDTO commentDTO, String account);*/
}
