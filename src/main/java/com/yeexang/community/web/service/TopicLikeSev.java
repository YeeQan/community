package com.yeexang.community.web.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.dao.TopicLikeDao;
import com.yeexang.community.pojo.po.TopicLike;
import com.yeexang.community.web.service.base.BaseSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/7/25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicLikeSev extends BaseSev<TopicLike, String> {

    @Autowired
    private TopicLikeDao topicLikeDao;

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.TOPIC_LIKE;
    }

    @Override
    public BaseMapper<TopicLike> getBaseMapper() {
        return topicLikeDao;
    }

    @Override
    public Class<TopicLike> getEntityClass() {
        return TopicLike.class;
    }

    public void saveTopicLikeStatus(String topicId, String account) {
        try {
            String id = topicId + "_" + account;
            TopicLike topicLike = selectById(id);
            if (topicLike == null) {
                topicLike = new TopicLike();
                topicLike.setId(id);
                topicLike.setTopicId(topicId);
                topicLike.setAccount(account);
                topicLike.setLikeFlag(true);
            } else {
                topicLike.setLikeFlag(!topicLike.isLikeFlag());
            }
            save(topicLike, id);
        } catch (Exception e) {
            log.error("TopicLikeSev saveTopicLikeStatus errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    public boolean getTopicLikeStatus(String topicId, String account) {
        boolean flag = false;
        try {
            String id = topicId + "_" + account;
            TopicLike topicLike = selectById(id);
            if (topicLike != null) {
                flag = topicLike.isLikeFlag();
            }
        } catch (Exception e) {
            log.error("TopicLikeSev getTopicLikeStatus errorMsg: {}", e.getMessage(), e);
        }
        return flag;
    }

    public Optional<TopicLike> getTopicLikeOne(String topicId, String account) {
        TopicLike topicLike;
        try {
            String id = topicId + "_" + account;
            topicLike = selectById(id);
        } catch (Exception e) {
            log.error("TopicLikeSev getTopicLikeOne errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(topicLike);
    }
}
