package com.yeexang.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
public class User {

    /**
     * 主键
     */
    private String id;

    /**
     * 账号，唯一，用于登录
     */
    private String account;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 经验值
     */
    private Integer exp;

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
