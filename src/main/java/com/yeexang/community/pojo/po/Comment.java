package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.CommentVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 评论 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@Slf4j
@TableName("y_c_comment")
@EqualsAndHashCode(callSuper = false)
public class Comment extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 评论id
     */
    @TableId("comment_id")
    private String commentId;

    /**
     * 父id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String commentContent;

    /**
     * 评论数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 评论类型，1为一级评论，2为二级评论
     */
    @TableField("comment_type")
    private String commentType;

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
        CommentVO commentVO;
        try {
            commentVO = new CommentVO();
            commentVO.setCommentId(commentId);
            commentVO.setParentId(parentId);
            commentVO.setCommentContent(commentContent);
            commentVO.setCommentCount(commentCount);
            commentVO.setLikeCount(likeCount);
            commentVO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception e) {
            log.error("Comment toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(commentVO);
    }
}
