package com.yeexang.community.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
public class Section {

    /**
     * 主键
     */
    private String id;

    /**
     * 分区id
     */
    private String sectionId;

    /**
     * 分区名
     */
    private String sectionName;

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
