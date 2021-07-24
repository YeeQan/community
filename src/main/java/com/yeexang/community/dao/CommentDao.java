package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/20
 */
@Repository
public interface CommentDao {

    void insert(Comment comment);

    void delete(Comment comment);

    void update(Comment comment);

    List<Comment> select(Comment comment);
}
