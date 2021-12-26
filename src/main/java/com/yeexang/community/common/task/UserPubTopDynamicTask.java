package com.yeexang.community.common.task;

import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.SpringBeanUtil;
import com.yeexang.community.dao.UserDynamicDao;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.UserDynamic;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户发布帖子动态Task
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Slf4j
public class UserPubTopDynamicTask implements Runnable {

    private final Topic topic;

    private final UserDynamicDao userDynamicDao = SpringBeanUtil.getBean(UserDynamicDao.class);

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    public UserPubTopDynamicTask(Topic topic) {
        this.topic = topic;
    }

    @Override
    public void run() {
        try {
            synchronized (UserPubTopDynamicTask.class) {
                UserDynamic userDynamic = new UserDynamic();
                userDynamic.setId(commonUtil.uuid());
                userDynamic.setDynamicId(commonUtil.randomCode());
                userDynamic.setAccount(topic.getCreateUser());
                userDynamic.setTargetId(topic.getTopicId());
                userDynamic.setDynamicType(CommonField.USER_PUBLIC_TOPIC_DYNAMIC_TYPE);
                userDynamic.setCreateTime(new Date());
                userDynamic.setUpdateTime(new Date());
                userDynamicDao.insert(userDynamic);
            }
        } catch (Exception e) {
            log.error("UserPubTopDynamicTask run errorMsg: {}", e.getMessage(), e);
        }
    }
}
