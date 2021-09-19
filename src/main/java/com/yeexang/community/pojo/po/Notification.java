package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 通知 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Notification extends BasePO {

    /**
     * 主键
     */
    private String id;

    /**
     * 通知id
     */
    private String notificationId;

    /**
     * 通知者
     */
    private String notifier;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 通知所属
     */
    private String outerId;

    /**
     * 通知类型，1为评论帖子，2为回复评论，3为点赞帖子
     */
    private String notificationType;

    /**
     * 状态，0为未读，1为已读
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    @Override
    public Optional<BaseDTO> toDTO() {
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
}
