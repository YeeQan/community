package com.yeexang.community.common.task;

import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.SpringBeanUtil;
import com.yeexang.community.dao.UserDynamicDao;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.po.UserDynamic;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户点赞帖子动态Task
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Slf4j
public class UserRegisterTask implements Runnable {

    private final User user;

    private final UserDynamicDao userDynamicDao = SpringBeanUtil.getBean(UserDynamicDao.class);

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    public UserRegisterTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            synchronized (UserRegisterTask.class) {
                UserDynamic userDynamic = new UserDynamic();
                userDynamic.setId(commonUtil.uuid());
                userDynamic.setDynamicId(commonUtil.randomCode());
                userDynamic.setAccount(user.getAccount());
                userDynamic.setTargetId(user.getAccount());
                userDynamic.setDynamicType(CommonField.USER_REGISTER_DYNAMIC_TYPE);
                userDynamic.setCreateTime(new Date());
                userDynamic.setUpdateTime(new Date());
                userDynamicDao.insert(userDynamic);
            }
        } catch (Exception e) {
            log.error("UserRegisterTask run errorMsg: {}", e.getMessage(), e);
        }
    }
}
