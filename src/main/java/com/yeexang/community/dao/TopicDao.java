package com.yeexang.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子管理 Dao
 *
 * @author yeeq
 * @date 2021/7/20
 */
public interface TopicDao extends BaseMapper<Topic> {

}
