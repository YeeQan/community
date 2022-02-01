package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.TopicVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 帖子 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@Slf4j
@TableName("y_c_topic")
@EqualsAndHashCode(callSuper = false)
public class Topic extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 帖子id
     */
    @TableId("topic_id")
    private String topicId;

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
     * 分区
     */
    @TableField("section")
    private String section;

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
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 加精标识
     */
    @TableField("essential_status")
    private Boolean essentialStatus;

    /**
     * 推荐标识
     */
    @TableField("recommended_status")
    private Boolean recommendedStatus;

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

    @Override
    public Optional<BaseVO> toVO() {
        TopicVO topicVO;
        try {
            topicVO = new TopicVO();
            topicVO.setTopicId(topicId);
            topicVO.setTopicTitle(topicTitle);
            topicVO.setTopicContent(topicContent);
            topicVO.setSection(section);
            topicVO.setCommentCount(commentCount);
            topicVO.setLikeCount(likeCount);
            topicVO.setViewCount(viewCount);
            topicVO.setEssentialStatus(essentialStatus);
            topicVO.setRecommendedStatus(recommendedStatus);
            topicVO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception e) {
            log.error("Topic toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(topicVO);
    }
}
