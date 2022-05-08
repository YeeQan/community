package com.yeexang.community.common.task;

import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.SpringBeanUtil;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 消息通知Task
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Slf4j
public class SendNotificationTask implements Runnable {

    private final String notifier;

    private final String outerId;

    private final String innerId;

    private final String type;

    private final NotificationSev notificationSev = SpringBeanUtil.getBean(NotificationSev.class);

    private final CommonUtil commonUtil = SpringBeanUtil.getBean(CommonUtil.class);

    public SendNotificationTask(String notifier, String outerId, String innerId, String type) {
        this.notifier = notifier;
        this.outerId = outerId;
        this.type = type;
        this.innerId = innerId;
    }

    @Override
    public void run() {
        try {
            synchronized (SendNotificationTask.class) {
                Notification notification = new Notification();
                notification.setId(commonUtil.uuid());
                notification.setNotificationId(commonUtil.randomCode());
                notification.setNotifier(notifier);
                notification.setOuterId(outerId);
                notification.setInnerId(innerId);
                notification.setNotificationType(type);
                notification.setStatus("0");
                notification.setCreateUser(notifier);
                notification.setCreateTime(new Date());
                notification.setUpdateUser(notifier);
                notification.setUpdateTime(new Date());
                notificationSev.receive(notification);
            }
        } catch (Exception e) {
            log.error("NotificationSev sendNotification errorMsg: {}", e.getMessage(), e);
        }
    }
}
