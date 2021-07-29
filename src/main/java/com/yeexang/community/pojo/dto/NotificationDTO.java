package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Notification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

/**
 * @author yeeq
 * @date 2021/7/29
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class NotificationDTO extends BaseDTO {

    private String notificationId;
    private String notifier;
    private String notifierName;
    private String receiver;
    private String receiverName;
    private String outerId;
    private String outerName;
    private String notificationType;
    private Boolean status;
    private String createTime;

    @Override
    public BasePO toPO() {
        Notification notification = new Notification();
        try {
            notification.setNotificationId(notificationId);
            notification.setNotifier(notifier);
            notification.setReceiver(receiver);
            notification.setOuterId(outerId);
            notification.setNotificationType(notificationType);
            notification.setStatus(status);
            if (StringUtils.isEmpty(createTime)) {
                notification.setCreateTime(null);
            } else {
                notification.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
            }
        } catch (Exception e) {
            log.error("NotificationDTO toPO errorMsg: {}", e.getMessage());
        }
        return notification;
    }
}
