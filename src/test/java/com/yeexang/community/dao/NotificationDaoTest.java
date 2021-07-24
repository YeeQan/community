package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Notification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/7/21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationDaoTest {

    @Autowired
    private NotificationDao notificationDao;

    @Test
    public void testInsert() {
        Notification notification = new Notification();
        notification.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        notification.setNotificationId("123456");
        notification.setNotifier("123456");
        notification.setReceiver("123456");
        notification.setOuterId("123456");
        notification.setNotificationType("1");
        notification.setStatus(false);
        notification.setCreateTime(new Date());
        notification.setCreateUser("123456");
        notification.setUpdateTime(new Date());
        notification.setUpdateUser("123456");
        notification.setDelFlag(false);
        notificationDao.insert(notification);
    }

    @Test
    public void testDelete() {
        Notification notification = new Notification();
        notification.setNotificationId("123456");
        notificationDao.delete(notification);
    }

    @Test
    public void testUpdate() {
        Notification notification = new Notification();
        notification.setNotificationId("123456");
        notification.setStatus(true);
        notificationDao.update(notification);
    }

    @Test
    public void testSelect() {
        Notification notification = new Notification();
        List<Notification> select = notificationDao.select(notification);
        for (Notification notification1 : select) {
            log.info("NotificationDaoTest testSelect : {}", notification1.toString());
        }
    }
}
