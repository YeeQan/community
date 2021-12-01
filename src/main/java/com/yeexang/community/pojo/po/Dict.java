package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 字典 PO
 *
 * @author yeeq
 * @date 2021/8/8
 */
@Data
@TableName("y_c_dict")
public class Dict {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * dict 主键
     */
    @TableId("dict_id")
    private String dictId;

    /**
     * 标签
     */
    @TableField("label")
    private String label;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序标识
     */
    @TableField("sort_flag")
    private Integer sortFlag;

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

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Boolean delFlag;
}
