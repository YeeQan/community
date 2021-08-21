package com.yeexang.community.web.service.impl;

import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.NotificationDao;
import com.yeexang.community.dao.UserDao;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private CommonUtil commonUtil;

    @Override
    public void setNotify(NotificationDTO notificationDTO) {
        Notification notification = (Notification) notificationDTO.toPO();
        try {
            notification.setId(commonUtil.uuid());
            String notificationId = commonUtil.randomCode();
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
            log.error("NotificationSev setNotify errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public List<Notification> receive(NotificationDTO notificationDTO) {
        Notification notification = (Notification) notificationDTO.toPO();
        List<Notification> notificationList = new ArrayList<>();
        try {
            List<Notification> notificationDBList = notificationDao.select(notification);
            notificationList.addAll(notificationDBList);
        } catch (Exception e) {
            log.error("NotificationSev receive errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return notificationList;
    }
}