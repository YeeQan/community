package com.styeeqan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.styeeqan.community.pojo.po.UserContribute;
import org.apache.ibatis.annotations.Param;

public interface UserContributeMapper extends BaseMapper<UserContribute> {

    void incrUserContributeAll(@Param("account") String account, @Param("contributeAll") int contributeAll);
}
