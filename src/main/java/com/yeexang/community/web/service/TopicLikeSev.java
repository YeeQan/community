package com.yeexang.community.web.service;

import com.yeexang.community.pojo.po.TopicLike;

import java.util.Optional;

/**
 * 帖子点赞管理 Service
 *
 * @author yeeq
 * @date 2021/7/25
 */
public interface TopicLikeSev {

    void saveTopicLikeStatus(String topicId, String account);

    boolean getTopicLikeStatus(String topicId, String account);

    Optional<TopicLike> getTopicLikeOne(String topicId, String account);
}
