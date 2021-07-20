package com.yeexang.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
public class Notification {

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
     * 通知类型，1为评论帖子，2为回复评论
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
}
