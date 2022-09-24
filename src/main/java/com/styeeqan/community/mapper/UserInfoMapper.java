package com.styeeqan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.styeeqan.community.pojo.po.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectbyAccount(String account);
}
