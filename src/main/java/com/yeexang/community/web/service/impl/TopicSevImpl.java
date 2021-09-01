package com.yeexang.community.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.NotificationSev;
import com.yeexang.community.web.service.TopicSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public PageInfo<Topic> getPage(Integer pageNum, Integer pageSize, TopicDTO topicDTO) {
        Topic topic = (Topic) topicDTO.toPO();
        PageInfo<Topic> pageInfo;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Topic> list = topicDao.select(topic);
            pageInfo = new PageInfo<>(list);
        } catch (Exception e) {
            log.error("TopicSev getPage errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new PageInfo<>();
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
            topicDao.updateVisitCountIncrease(topicDTO.getTopicId());
        } catch (Exception e) {
            log.error("TopicSev getTopic errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }

    @Override
    public List<Topic> publish(TopicDTO topicDTO, String account) {
        Topic topic = (Topic) topicDTO.toPO();
        List<Topic> topicList = new ArrayList<>();
        try {
            topic.setId(commonUtil.uuid());
            String topicId = commonUtil.randomCode();
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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }

    @Override
    public List<Topic> like(TopicDTO topicDTO, String account) {
        String topicId = topicDTO.getTopicId();
        List<Topic> topicList = new ArrayList<>();
        try {
            topicDao.updateLikeCountIncrease(topicId);
            Topic topicParam = new Topic();
            topicParam.setTopicId(topicId);
            List<Topic> topicDBList = topicDao.select(topicParam);
            topicList.addAll(topicDBList);

            Topic topicDB = topicDBList.get(0);
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotifier(account);
            notificationDTO.setReceiver(topicDB.getCreateUser());
            notificationDTO.setOuterId(topicDB.getTopicId());
            notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_LIKE_TOPIC);
            notificationSev.setNotify(notificationDTO);
        } catch (Exception e) {
            log.error("TopicSev like errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }
}
