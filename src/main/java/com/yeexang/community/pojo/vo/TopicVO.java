package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TopicVO extends BaseVO {

    private String topicId;
    private String topicTitle;
    private String topicContent;
    private String section;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean essentialStatus;
    private Boolean recommendedStatus;
    private String createTime;
    private String createUserName;
    private String headPortrait;
    private Boolean likeStatus;
    private Boolean createrVisit;
    private String createrHomepageId;

    private List<CommentVO> commentVOList;
}
