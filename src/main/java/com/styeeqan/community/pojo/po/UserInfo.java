package com.styeeqan.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
@TableName("y_c_user_info")
public class UserInfo {

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("account")
    private String account;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 个人介绍
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 公司
     */
    @TableField("company")
    private String company;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 用户头像 AliyunOSS url
     */
    @TableField("head_portrait")
    private String headPortrait;

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
}
