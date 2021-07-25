package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/25
 */
public interface TopicSev {

    List<Topic> publish(TopicDTO topicDTO, String account);
}
