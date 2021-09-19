package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论管理 Dao
 *
 * @author yeeq
 * @date 2021/7/20
 */
@Repository
public interface CommentDao {

    void insert(Comment comment);

    void delete(Comment comment);

    void update(Comment comment);

    /**
     * 点赞数加一
     * @param commentId commentId
     */
    void updateLikeCountIncrease(@Param("commentId") String commentId);

    /**
     * 评论数加一
     * @param commentId commentId
     */
    void updateCommentCountIncrease(@Param("commentId") String commentId);

    List<Comment> select(Comment comment);


}
