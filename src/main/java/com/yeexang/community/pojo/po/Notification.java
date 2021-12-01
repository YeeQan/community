package com.yeexang.community.pojo.po;

/**
 * 通知 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
/*@Data
@Slf4j
@TableName("y_c_notification")
@EqualsAndHashCode(callSuper = false)*/
/*public class Notification extends BasePO {

    *//**
     * 主键
     *//*
    @TableId("id")
    private String id;

    *//**
     * 通知id
     *//*
    @TableField("notification_id")
    private String notificationId;

    *//**
     * 通知者
     *//*
    @TableField("notifier")
    private String notifier;

    *//**
     * 接收者
     *//*
    @TableField("receiver")
    private String receiver;

    *//**
     * 通知所属
     *//*
    @TableField("outer_id")
    private String outerId;

    *//**
     * 通知类型，1为评论帖子，2为回复评论，3为点赞帖子
     *//*
    @TableField("notification_type")
    private String notificationType;

    *//**
     * 状态，0为未读，1为已读
     *//*
    @TableField("status")
    private Boolean status;

    *//**
     * 创建时间
     *//*
    @TableField("create_time")
    private Date createTime;

    *//**
     * 创建者
     *//*
    @TableField("create_user")
    private String createUser;

    *//**
     * 更新时间
     *//*
    @TableField("update_time")
    private Date updateTime;

    *//**
     * 更新者
     *//*
    @TableField("update_user")
    private String updateUser;

    *//**
     * 删除标识
     *//*
    @TableField("del_flag")
    private Boolean delFlag;

    @Override
    public Optional<BaseDTO> toVO() {
        NotificationDTO notificationDTO;
        try {
            notificationDTO = new NotificationDTO();
            notificationDTO.setNotificationId(notificationId);
            notificationDTO.setNotifier(notifier);
            notificationDTO.setReceiver(receiver);
            notificationDTO.setOuterId(outerId);
            notificationDTO.setNotificationType(notificationType);
            notificationDTO.setStatus(status);
            notificationDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception e) {
            log.error("Notification toDTO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(notificationDTO);
    }
}*/
