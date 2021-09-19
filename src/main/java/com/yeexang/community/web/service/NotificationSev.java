package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Notification;

import java.util.List;

/**
 * 通知管理 Service
 *
 * @author yeeq
 * @date 2021/7/29
 */
public interface NotificationSev {

    /**
     * 设置通知
     * @param notificationDTO notificationDTO
     */
    void setNotify(NotificationDTO notificationDTO);

    /**
     * 接收通知
     * @param notificationDTO notificationDTO
     * @return List<Notification>
     */
    List<Notification> receive(NotificationDTO notificationDTO);
}
