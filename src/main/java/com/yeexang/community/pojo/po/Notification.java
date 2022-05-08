package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.NotificationVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2022/4/6
 */
@Data
@Slf4j
@TableName("y_c_notification")
@EqualsAndHashCode(callSuper = false)
public class Notification extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 通知id
     */
    @TableId("notification_id")
    private String notificationId;

    /**
     * 通知者
     */
    @TableField("notifier")
    private String notifier;

    /**
     * 接收者
     */
    @TableField("receiver")
    private String receiver;

    /**
     * 通知所属id
     */
    @TableField("outer_id")
    private String outerId;

    /**
     * 通知内容id
     */
    @TableField("inner_id")
    private String innerId;

    /**
     * 通知类型,1为评论帖子,2为回复评论
     */
    @TableField("notification_type")
    private String notificationType;

    /**
     * 状态,0为未读,1为已读
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新者
     */
    @TableField("update_user")
    private String updateUser;

    @Override
    public Optional<BaseVO> toVO() {
        NotificationVO notificationVO;
        try {
            notificationVO = new NotificationVO();
            notificationVO.setNotificationId(notificationId);
            notificationVO.setNotifier(notifier);
            notificationVO.setOuterId(outerId);
            notificationVO.setNotificationType(notificationType);
            notificationVO.setStatus(status);
            notificationVO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd").format(updateTime));
        } catch (Exception e) {
            log.error("Notification toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(notificationVO);
    }
}
