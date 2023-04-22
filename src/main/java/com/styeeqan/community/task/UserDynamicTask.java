package com.styeeqan.community.task;

import com.styeeqan.community.common.util.CommonUtil;
import com.styeeqan.community.common.util.SpringBeanUtil;
import com.styeeqan.community.mapper.UserDynamicMapper;
import com.styeeqan.community.pojo.po.UserDynamic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDynamicTask implements Runnable {

    private String type;

    private String targetId;

    private String sourceId;

    private String createUser;

    private String updateUser;

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    private final UserDynamicMapper userDynamicMapper = SpringBeanUtil.getBean(UserDynamicMapper.class);

    @Override
    public void run() {
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
