package com.yeexang.community.common.task;

import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.SpringBeanUtil;
import com.yeexang.community.dao.UserDynamicDao;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.UserDynamic;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 用户发布评论动态Task
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Slf4j
public class UserPubComDynamicTask implements Runnable {

    private final Comment comment;

    private final UserDynamicDao userDynamicDao = SpringBeanUtil.getBean(UserDynamicDao.class);

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    public UserPubComDynamicTask(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void run() {
        try {
            synchronized (UserPubComDynamicTask.class) {
                UserDynamic userDynamic = new UserDynamic();
                userDynamic.setId(commonUtil.uuid());
                userDynamic.setDynamicId(commonUtil.randomCode());
                userDynamic.setAccount(comment.getCreateUser());
                userDynamic.setTargetId(comment.getCommentId());
                userDynamic.setDynamicType(CommonField.USER_PUBLIC_COMMENT_DYNAMIC_TYPE);
                userDynamic.setCreateTime(new Date());
                userDynamic.setUpdateTime(new Date());
                userDynamicDao.insert(userDynamic);
            }
        } catch (Exception e) {
            log.error("UserPubComDynamicTask run errorMsg: {}", e.getMessage(), e);
        }
    }
}
