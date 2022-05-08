package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author yeeq
 * @date 2022/4/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationVO extends BaseVO {

    private String notificationId;
    private String notifier;
    private String notifierName;
    private String receiverHeadPortrait;
    private String outerId;
    private String outerName;
    private String innerName;
    private String notificationType;
    private String status;
    private String updateTime;
}
