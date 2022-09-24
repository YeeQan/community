package com.styeeqan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.styeeqan.community.pojo.po.Topic;

public interface TopicMapper extends BaseMapper<Topic> {

    void incrViewCount(String topicId);

    void incrCommentCount(String topicId);
}
