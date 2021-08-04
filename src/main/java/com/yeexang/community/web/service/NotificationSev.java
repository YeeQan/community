package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Notification;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/29
 */
public interface NotificationSev {

    void setNotify(NotificationDTO notificationDTO);

    List<Notification> receive(NotificationDTO notificationDTO);
}
