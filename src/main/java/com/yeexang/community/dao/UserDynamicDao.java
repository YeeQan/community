package com.yeexang.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.pojo.po.UserDynamic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户动态 Dao
 *
 * @author yeeq
 * @date 2021/12/11
 */
public interface UserDynamicDao extends BaseMapper<UserDynamic> {

    List<UserDynamic> selectUserDynamicByAccount(@Param("account") String account);
}
