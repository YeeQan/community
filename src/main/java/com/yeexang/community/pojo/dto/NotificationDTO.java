package com.yeexang.community.pojo.dto;

/**
 * 通知 DTO
 *
 * @author yeeq
 * @date 2021/7/29
 */
/*@Data
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
    public Optional<BasePO> toPO() {
        Notification notification;
        try {
            notification = new Notification();
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
            log.error("NotificatinDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(notification);
    }
}*/
