package com.yeexang.community.common.task;

import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.SpringBeanUtil;
import com.yeexang.community.dao.UserDynamicDao;
import com.yeexang.community.pojo.po.UserDynamic;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户动态Task
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Slf4j
public class UserDynamicTask implements Runnable {

    private final String account;

    private final String targetId;

    private final String dynamicType;

    private final UserDynamicDao userDynamicDao = SpringBeanUtil.getBean(UserDynamicDao.class);

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    public UserDynamicTask(String targetId, String account, String dynamicType) {
        this.targetId = targetId;
        this.account = account;
        this.dynamicType = dynamicType;
    }

    @Override
    public void run() {
        try {
            synchronized (UserDynamicTask.class) {
                UserDynamic userDynamic = new UserDynamic();
                userDynamic.setId(commonUtil.uuid());
                userDynamic.setDynamicId(commonUtil.randomCode());
                userDynamic.setAccount(account);
                userDynamic.setTargetId(targetId);
                userDynamic.setDynamicType(dynamicType);
                userDynamic.setCreateTime(new Date());
                userDynamic.setUpdateTime(new Date());
                userDynamicDao.insert(userDynamic);
            }
        } catch (Exception e) {
            log.error("UserDynamicTask run errorMsg: {}", e.getMessage(), e);
        }
    }
}
