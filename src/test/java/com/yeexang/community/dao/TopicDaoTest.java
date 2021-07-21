package com.yeexang.community.dao;

import com.yeexang.community.entity.Topic;
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
public class TopicDaoTest {

    @Autowired
    private TopicDao topicDao;

    @Test
    public void testInsert() {
        Topic topic = new Topic();
        topic.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        topic.setTopicId("123456");
        topic.setTopicTitle("测试标题");
        topic.setTopicContent("测试内容");
        topic.setSection("1");
        topic.setCommentCount(1);
        topic.setViewCount(1);
        topic.setLikeCount(1);
        topic.setEssentialStatus(false);
        topic.setRecommendedStatus(false);
        topic.setLastCommentTime(new Date());
        topic.setCreateTime(new Date());
        topic.setCreateUser("123456");
        topic.setUpdateTime(new Date());
        topic.setUpdateUser("123456");
        topic.setDelFlag(false);
        topicDao.insert(topic);
    }

    @Test
    public void testDelete() {
        Topic topic = new Topic();
        topic.setTopicId("123456");
        topicDao.delete(topic);
    }

    @Test
    public void testUpdate() {
        Topic topic = new Topic();
        topic.setTopicId("123456");
        topic.setTopicTitle("测试标题2");
        topicDao.update(topic);
    }

    @Test
    public void testSelect() {
        Topic topic = new Topic();
        List<Topic> select = topicDao.select(topic);
        for (Topic topic1 : select) {
            log.info("TopicDaoTest testSelect : {}", topic1.toString());
        }
    }
}
