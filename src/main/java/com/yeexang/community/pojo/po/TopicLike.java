package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 帖子点赞 PO
 *
 * @author yeeq
 * @date 2021/12/7
 */
@Data
@TableName("y_c_topic_like")
public class TopicLike {

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 帖子 id
     */
    @TableField("topic_id")
    private String topicId;

    /**
     * 用户 id
     */
    @TableField("account")
    private String account;

    /**
     * 是否已经点赞
     */
    @TableField("like_flag")
    private boolean likeFlag;
}
