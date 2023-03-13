package com.styeeqan.community.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.common.util.SpringBeanUtil;
import com.styeeqan.community.mapper.UserDynamicMapper;
import com.styeeqan.community.pojo.po.UserDynamic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDynamicTask implements Serializable {

    private String type;

    private String targetId;

    private String sourceId;

    private String createUser;

    private String updateUser;

    @JsonIgnore
    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    @JsonIgnore
    private final UserDynamicMapper userDynamicMapper = SpringBeanUtil.getBean(UserDynamicMapper.class);

    public void execute() {
        UserDynamic userDynamic = new UserDynamic();
        userDynamic.setId(commonUtil.randomCode());
        userDynamic.setType(type);
        userDynamic.setTargetId(targetId);
        userDynamic.setSourceId(sourceId);
        userDynamic.setCreateUser(createUser);
        userDynamic.setCreateTime(new Date());
        userDynamic.setUpdateUser(updateUser);
        userDynamic.setUpdateTime(new Date());
        userDynamicMapper.insert(userDynamic);
    }
}
