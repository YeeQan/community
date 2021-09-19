package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Integer commentCount;
    private Integer likeCount;
    private String commentType;
    private String createUser;
    private String createUsername;
    private String createTime;

    @Override
    public Optional<BasePO> toPO() {
        Comment comment;
        try {
            comment = new Comment();
            comment.setCommentId(commentId);
            comment.setParentId(parentId);
            comment.setCommentContent(commentContent);
            comment.setCommentCount(commentCount);
            comment.setLikeCount(likeCount);
            comment.setCommentType(commentType);
            comment.setCreateUser(createUser);
            if (StringUtils.isEmpty(createTime)) {
                comment.setCreateTime(null);
            } else {
                comment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime));
            }
        } catch (Exception e) {
            log.error("CommentDTO toPO errorMsg: {}", e.getMessage());
            return Optional.empty();
        }
        return Optional.of(comment);
    }
}
