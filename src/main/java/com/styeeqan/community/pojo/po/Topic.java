package com.styeeqan.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("y_c_topic")
public class Topic {

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 标题
     */
    @TableField("topic_title")
    private String topicTitle;

    /**
     * 内容
     */
    @TableField("topic_content")
    private String topicContent;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 浏览数
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 最后一次评论时间
     */
    @TableField("last_comment_time")
    private Date lastCommentTime;

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
