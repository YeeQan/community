package com.yeexang.community.dao;

import com.yeexang.community.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/7/21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void testInsert() {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        comment.setCommentId("123456");
        comment.setParentId("123456");
        comment.setCommentContent("testContent");
        comment.setCommentCount(1);
        comment.setLikeCount(1);
        comment.setCommentType("1");
        comment.setCommentType("1");
        comment.setCreateTime(new Date());
        comment.setCreateUser("123456");
        comment.setUpdateTime(new Date());
        comment.setUpdateUser("123456");
        comment.setDelFlag(false);
        commentDao.insert(comment);
    }

    @Test
    public void testDelete() {
        Comment comment = new Comment();
        comment.setCommentId("123456");
        commentDao.delete(comment);
    }

    @Test
    public void testUpdate() {
        Comment comment = new Comment();
        comment.setCommentId("123456");
        comment.setCommentType("2");
        commentDao.update(comment);
    }

    @Test
    public void testSelect() {
        Comment comment = new Comment();
        List<Comment> select = commentDao.select(comment);
        for (Comment comment1 : select) {
            log.info("CommentDaoTest testSelect : {}", comment1.toString());
        }
    }
}
