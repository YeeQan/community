package com.yeexang.community.web.service;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;

import java.util.List;

/**
 * 帖子管理 Service
 *
 * @author yeeq
 * @date 2021/7/25
 */
public interface TopicSev {

    /**
     * 分页获取
     * @param pageNum 页码
     * @param pageSize 分页大小
     * @param topicDTO 筛选条件
     * @return PageInfo<Topic>
     */
    PageInfo<Topic> getPage(Integer pageNum, Integer pageSize, TopicDTO topicDTO);

    /**
     * 获取帖子
     * @param topicDTO topicDTO
     * @return List<Topic>
     */
    List<Topic> getTopic(TopicDTO topicDTO);

    /**
     * 发布帖子
     * @param topicDTO topicDTO
     * @param account account
     * @return List<Topic>
     */
    List<Topic> publish(TopicDTO topicDTO, String account);

    /**
     * 点赞
     * @param topicDTO topicDTO
     * @param account account
     * @return List<Topic>
     */
    List<Topic> like(TopicDTO topicDTO, String account);
}
