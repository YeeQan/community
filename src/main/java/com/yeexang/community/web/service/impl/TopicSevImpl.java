package com.yeexang.community.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.CommonField;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.NotificationSev;
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

    @Autowired
    private NotificationSev notificationSev;

    @Override
    public PageInfo<Topic> getPage(Integer pageNum, Integer pageSize, TopicDTO topicDTO) {
        Topic topic = (Topic) topicDTO.toPO();
        PageInfo<Topic> pageInfo = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Topic> list = topicDao.select(topic);
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            log.error("TopicSev list errorMsg: {}", e.getMessage());
        }
        return pageInfo;
    }

    @Override
    public List<Topic> getTopic(TopicDTO topicDTO) {
        Topic topic = (Topic) topicDTO.toPO();
        List<Topic> topicList = new ArrayList<>();
        try {
            List<Topic> topicDBList = topicDao.select(topic);
            topicList.addAll(topicDBList);
        } catch (Exception e) {
            log.error("TopicSev getTopic errorMsg: {}", e.getMessage());
        }
        return topicList;
    }

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
            log.error("TopicSev publish errorMsg: {}", e.getMessage());
        }
        return topicList;
    }

    @Override
    public List<Topic> like(TopicDTO topicDTO, String account) {
        String topicId = topicDTO.getTopicId();
        List<Topic> topicList = new ArrayList<>();
        try {
            topicDao.updateCountIncrease(topicId);
            Topic topicParam = new Topic();
            topicParam.setTopicId(topicId);
            List<Topic> topicDBList = topicDao.select(topicParam);
            topicList.addAll(topicDBList);

            Topic topicDB = topicDBList.get(0);
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotifier(account);
            notificationDTO.setReceiver(topicDB.getCreateUser());
            notificationDTO.setOuterId(topicDB.getTopicId());
            notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_LIKE);
            notificationSev.setNotify(notificationDTO);
        } catch (Exception e) {
            log.error("TopicSev like errorMsg: {}", e.getMessage());
        }
        return topicList;
    }
}
