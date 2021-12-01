package com.yeexang.community.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeexang.community.common.filter.Filter;
import com.yeexang.community.pojo.po.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yeeq
 * @date 2021/11/30
 */
@Slf4j
@Component
public class FilterUtil {

    /**
     * 设置筛选条件
     * @param filter filter
     * @param queryWrapper queryWrapper
     */
    public void setFilterCondition(Filter filter, QueryWrapper queryWrapper) {
        if (filter == null) {
            return;
        }
        if (filter.isCreateTimeAsc()) {
            setCreateTimeAsc(queryWrapper);
        }
        if (filter.isCreateTimeDesc()) {
            setCreateTimeDesc(queryWrapper);
        }
        if (filter.isTopicEssential()) {
            setTopicEssential(queryWrapper);
        }
    }

    /**
     * 创建时间正序
     */
    private void setCreateTimeAsc(QueryWrapper queryWrapper) {
        queryWrapper.orderByAsc("create_time");
    }

    /**
     * 创建时间倒序
     */
    private void setCreateTimeDesc(QueryWrapper queryWrapper) {
        queryWrapper.orderByDesc("create_time");
    }

    /**
     * 筛选加精的帖子
     */
    private void setTopicEssential(QueryWrapper queryWrapper) {
        Object entity = queryWrapper.getEntity();
        if (entity == null) {
            log.error("FilterUtil setTopicEssential errorMsg : {}", "Entity is null");
        } else {
            if (entity instanceof Topic && Topic.class.getName().equals(entity.getClass().getName())) {
                Topic topic = (Topic) entity;
                topic.setEssentialStatus(true);
                queryWrapper.setEntity(topic);
            } else {
                log.error("FilterUtil setTopicEssential errorMsg : {}", "Entity type is not Topic");
            }
        }
    }
}
