package com.yeexang.community.web.service;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;

import java.util.List;

/**
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

    List<Topic> getTopic(TopicDTO topicDTO);

    List<Topic> publish(TopicDTO topicDTO, String account);

    List<Topic> like(TopicDTO topicDTO, String account);
}
