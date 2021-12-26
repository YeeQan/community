package com.yeexang.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.pojo.po.UserHomepage;
import com.yeexang.community.pojo.po.ext.UserHomepageExt;
import org.apache.ibatis.annotations.Param;

/**
 * 用户个人主页 DAO
 *
 * @author yeeq
 * @date 2021/12/19
 */
public interface UserHomepageDao extends BaseMapper<UserHomepage> {

    UserHomepageExt selectUserHomepage(@Param("homepageId") String homepageId);
}
