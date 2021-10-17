package com.yeexang.community.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.NotificationSev;
import com.yeexang.community.web.service.TopicSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicSevImpl implements TopicSev {

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private NotificationSev notificationSev;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public PageInfo<Topic> getPage(Integer pageNum, Integer pageSize, TopicDTO topicDTO) {
        PageInfo<Topic> pageInfo = null;
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
                PageHelper.startPage(pageNum, pageSize);
                List<Topic> list = topicDao.select(topic);
                pageInfo = new PageInfo<>(list);
            }
        } catch (Exception e) {
            log.error("TopicSev getPage errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new PageInfo<>();
        }
        return pageInfo;
    }

    @Override
    public List<Topic> getTopic(TopicDTO topicDTO) {
        List<Topic> topicList = new ArrayList<>();
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
                List<Topic> topicDBList = topicDao.select(topic);
                topicList.addAll(topicDBList);
                topicDao.updateVisitCountIncrease(topicDTO.getTopicId());
            }
        } catch (Exception e) {
            log.error("TopicSev getTopic errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }

    @Override
    public List<Topic> publish(TopicDTO topicDTO, String account) {
        List<Topic> topicList = new ArrayList<>();
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
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
            }
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
            if (!topicDBList.isEmpty()) {
                topicList.addAll(topicDBList);
                Topic topicDB = topicDBList.get(0);
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setNotifier(account);
                notificationDTO.setReceiver(topicDB.getCreateUser());
                notificationDTO.setOuterId(topicDB.getTopicId());
                notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_LIKE_TOPIC);
                notificationSev.setNotify(notificationDTO);
            }
        } catch (Exception e) {
            log.error("TopicSev like errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }

    @Override
    public List<Topic> getList(TopicDTO topicDTO) {
        List<Topic> topicList = new ArrayList<>();
        try {
            Optional<BasePO> optional = topicDTO.toPO();
            if (optional.isPresent()) {
                Topic topic = (Topic) optional.get();
                List<Topic> topicDBList = topicDao.select(topic);
                topicList.addAll(topicDBList);
            }
        } catch (Exception e) {
            log.error("TopicSev getList errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return topicList;
    }
}
