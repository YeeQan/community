package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Notification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2022/4/19
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class NotificationDTO extends BaseDTO {

    private String notificationId;
    private String receiver;
    private String typeLabel;

    @Override
    public Optional<BasePO> toPO() {
        Notification notification;
        try {
            notification = new Notification();
            notification.setNotificationId(notificationId);
            notification.setReceiver(receiver);
        } catch (Exception e) {
            log.error("NotificationDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(notification);
    }
}
