package com.yeexang.community.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/8/8
 */
@Data
public class Dict {

    /**
     * 主键
     */
    private String id;

    /**
     * dict 主键
     */
    private String dictId;

    /**
     * 标签
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序标识
     */
    private Integer sortFlag;

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
