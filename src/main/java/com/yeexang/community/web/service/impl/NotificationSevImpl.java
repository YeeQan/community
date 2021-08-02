package com.yeexang.community.web.service.impl;

import com.yeexang.community.dao.NotificationDao;
import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/7/29
 */
@Slf4j
@Service
public class NotificationSevImpl implements NotificationSev {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void setNotify(NotificationDTO notificationDTO) {
        Notification notification = (Notification) notificationDTO.toPO();
        try {
            notification.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            int random = new Random().nextInt(1000000);
            String notificationId = dateStr + random;
            notification.setNotificationId(notificationId);
            notification.setNotificationId(notificationId);
            notification.setStatus(false);
            notification.setCreateTime(new Date());
            notification.setCreateUser(notificationDTO.getNotifier());
            notification.setUpdateTime(new Date());
            notification.setUpdateUser(notificationDTO.getNotifier());
            notification.setDelFlag(false);
            notificationDao.insert(notification);
        } catch (Exception e) {
            log.error("NotificationSev notify errorMsg: {}", e.getMessage());
        }
    }
}
