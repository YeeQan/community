package com.styeeqan.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("y_c_user_dynamic")
public class UserDynamic {

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 动态类型
     */
    @TableField("type")
    private String type;

    /**
     * 目标id
     */
    @TableField("target_id")
    private String targetId;

    /**
     * 源id
     */
    @TableField("source_id")
    private String sourceId;

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
