package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 评论 DTO
 *
 * @author yeeq
 * @date 2021/8/1
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class CommentDTO extends BaseDTO {

    private String commentId;
    private String parentId;
    private String commentContent;
    private String createUser;
    private String updateUser;
    private String commentType;

    @Override
    public Optional<BasePO> toPO() {
        Comment comment;
        try {
            comment = new Comment();
            comment.setCommentId(commentId);
            comment.setParentId(parentId);
            comment.setCommentContent(commentContent);
            comment.setCreateUser(createUser);
            comment.setUpdateUser(updateUser);
            comment.setCommentType(commentType);
        } catch (Exception e) {
            log.error("CommonDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(comment);
    }
}
