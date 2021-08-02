package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.CommentDTO;
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
public class Comment extends BasePO {

    /**
     * 主键
     */
    private String id;

    /**
     * 评论id
     */
    private String commentId;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论类型，1为一级评论，2为二级评论
     */
    private String commentType;

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
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(commentId);
        commentDTO.setParentId(parentId);
        commentDTO.setCommentContent(commentContent);
        commentDTO.setCommentCount(commentCount);
        commentDTO.setLikeCount(likeCount);
        commentDTO.setCommentType(commentType);
        commentDTO.setCreateUser(createUser);
        commentDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        return commentDTO;
    }
}
