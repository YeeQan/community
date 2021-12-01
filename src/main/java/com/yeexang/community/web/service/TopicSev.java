package com.yeexang.community.web.service;

import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.filter.Filter;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.vo.PageVO;
import com.yeexang.community.pojo.vo.TopicVO;

import java.util.List;
import java.util.Optional;

/**
 * 帖子管理 Service
 *
 * @author yeeq
 * @date 2021/7/25
 */
public interface TopicSev {

    /**
     * 获取帖子信息
     * @param pageNum pageNum
     * @param pageSize pageSize
     * @param topicDTO topicDTO
     * @param filter filter
     * @return PageInfo<Topic>
     */
    PageVO<TopicVO> getTopicList(Integer pageNum, Integer pageSize, TopicDTO topicDTO, Filter filter);

    /**
     * 访问帖子
     * @param topicId topicId
     * @return Optional<Topic>
     */
    Optional<TopicVO> visit(String topicId, String ipAddr);

    /**
     * 发布帖子
     * @param topicDTO topicDTO
     * @return Optional<Topic>
     */
    Optional<TopicVO> publish(TopicDTO topicDTO);

    /**
     * 增加评论次数
     * @param topicId topicId
     */
    void topicCommentCountIncrease(String topicId);
}
