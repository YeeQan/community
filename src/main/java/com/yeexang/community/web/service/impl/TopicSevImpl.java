package com.yeexang.community.web.service.impl;

import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.TopicSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@Service
public class TopicSevImpl implements TopicSev {

    @Autowired
    private TopicDao topicDao;

    @Override
    public List<Topic> publish(TopicDTO topicDTO, String account) {
        Topic topic = (Topic) topicDTO.toPO();
        List<Topic> topicList = new ArrayList<>();
        try {
            topic.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            int random = new Random().nextInt(1000000);
            String topicId = dateStr + random;
            topic.setTopicId(topicId);
            topic.setCommentCount(0);
            topic.setViewCount(0);
            topic.setLikeCount(0);
            topic.setEssentialStatus(false);
            topic.setRecommendedStatus(false);
            topic.setLastCommentTime(new Date());
            topic.setCreateTime(new Date());
            topic.setCreateUser(account);
            topic.setUpdateTime(new Date());
            topic.setUpdateUser(account);
            topic.setDelFlag(false);
            topicDao.insert(topic);

            Topic topicParam = new Topic();
            topicParam.setTopicId(topicId);
            List<Topic> topicDBList = topicDao.select(topicParam);
            topicList.addAll(topicDBList);
        } catch (Exception e) {
            log.error("UserSev register errorMsg: {}", e.getMessage());
        }
        return topicList;
    }
}
