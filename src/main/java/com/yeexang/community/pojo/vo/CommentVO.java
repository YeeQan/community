package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yeeq
 * @date 2021/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentVO extends BaseVO {

    private String commentId;
    private String parentId;
    private String commentContent;
    private Integer commentCount;
    private Integer likeCount;
    private String createUsername;
    private String createTime;
    private String headPortrait;
    private String createrHomepageId;
}
