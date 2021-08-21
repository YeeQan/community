package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Topic extends BasePO {

    /**
     * 主键
     */
    private String id;

    /**
     * 帖子id
     */
    private String topicId;

    /**
     * 标题
     */
    private String topicTitle;

    /**
     * 内容
     */
    private String topicContent;

    /**
     * 分区
     */
    private String section;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 加精标识
     */
    private Boolean essentialStatus;

    /**
     * 推荐标识
     */
    private Boolean recommendedStatus;

    /**
     * 最后一次评论时间
     */
    private Date lastCommentTime;

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

    @Override
    public BaseDTO toDTO() {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTopicId(topicId);
        topicDTO.setTopicTitle(topicTitle);
        topicDTO.setTopicContent(topicContent);
        topicDTO.setSection(section);
        topicDTO.setCommentCount(commentCount);
        topicDTO.setLikeCount(likeCount);
        topicDTO.setViewCount(viewCount);
        topicDTO.setEssentialStatus(essentialStatus);
        topicDTO.setRecommendedStatus(recommendedStatus);
        topicDTO.setLastCommentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastCommentTime));
        topicDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        topicDTO.setCreateUser(createUser);
        return topicDTO;
    }
}
