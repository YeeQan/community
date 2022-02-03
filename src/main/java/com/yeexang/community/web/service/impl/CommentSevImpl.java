package com.yeexang.community.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.task.UserPubComDynamicTask;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.common.util.ThreadUtil;
import com.yeexang.community.dao.CommentDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.po.UserHomepage;
import com.yeexang.community.pojo.po.ext.CommentExt;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.CommentVO;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.TopicSev;
import com.yeexang.community.web.service.impl.base.BaseSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentSevImpl extends BaseSev<Comment, String> implements CommentSev {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private ThreadUtil threadUtil;

    @Override
    protected RedisKey getRedisKey() {
        return RedisKey.COMMENT;
    }

    @Override
    protected BaseMapper<Comment> getBaseMapper() {
        return commentDao;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public List<CommentVO> getFirstLevelComment(CommentDTO commentDTO) {
        List<CommentVO> commentVOList;
        try {
            List<CommentExt> commentExtList
                    = commentDao.selectCommentListByParentIdAndType(commentDTO.getParentId(), CommonField.FIRST_LEVEL_COMMENT);
            commentVOList = commentExtList.stream()
                    .map(commentExt -> {
                        Comment comment = commentExt.getComment();
                        User user = commentExt.getUser();
                        UserHomepage userHomepage = commentExt.getUserHomepage();
                        CommentVO commentVO = null;
                        Optional<BaseVO> optional = comment.toVO();
                        if (optional.isPresent()) {
                            commentVO = (CommentVO) optional.get();
                            if (user != null) {
                                commentVO.setCreateUsername(user.getUsername());
                                commentVO.setHeadPortrait(user.getHeadPortrait());
                                commentVO.setCreaterHomepageId(userHomepage.getHomepageId());
                            }
                        }
                        return commentVO;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("CommentSev getFirstLevelComment errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return commentVOList;
    }

    @Override
    public List<CommentVO> getSecondLevelComment(CommentDTO commentDTO) {
        List<CommentVO> commentVOList;
        try {
            List<CommentExt> commentExtList
                    = commentDao.selectCommentListByParentIdAndType(commentDTO.getParentId(), CommonField.SECOND_LEVEL_COMMENT);
            commentVOList = commentExtList.stream()
                    .map(commentExt -> {
                        Comment comment = commentExt.getComment();
                        User user = commentExt.getUser();
                        UserHomepage userHomepage = commentExt.getUserHomepage();
                        CommentVO commentVO = null;
                        Optional<BaseVO> optional = comment.toVO();
                        if (optional.isPresent()) {
                            commentVO = (CommentVO) optional.get();
                            if (user != null) {
                                commentVO.setCreateUsername(user.getUsername());
                                commentVO.setHeadPortrait(user.getHeadPortrait());
                            }
                            commentVO.setCreaterHomepageId(userHomepage.getHomepageId());
                        }
                        return commentVO;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("CommentSev getSecondLevelComment errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return commentVOList;
    }

    @Override
    public void publish(CommentDTO commentDTO, String account) {
        try {
            Optional<BasePO> optional = commentDTO.toPO();
            if (optional.isPresent()) {
                Comment comment = (Comment) optional.get();
                comment.setId(commonUtil.uuid());
                String commentId = commonUtil.randomCode();
                comment.setCommentId(commentId);
                comment.setCommentCount(0);
                comment.setLikeCount(0);
                comment.setCreateTime(new Date());
                comment.setUpdateTime(new Date());
                comment.setCreateUser(account);
                comment.setUpdateUser(account);

                save(comment, comment.getCommentId());

                comment = selectById(comment.getCommentId());
                if (comment != null) {
                    // 增加评论数
                    String topicId = null;
                    if (CommonField.FIRST_LEVEL_COMMENT.equals(comment.getCommentType())) {
                        topicId = comment.getParentId();
                    } else if (CommonField.SECOND_LEVEL_COMMENT.equals(comment.getCommentType())) {
                        Comment parent = selectById(comment.getParentId());
                        // 增加父级评论的评论数
                        parentCommentCountIncrease(parent.getCommentId());
                        topicId = parent.getParentId();
                    }
                    topicSev.topicCommentCountIncrease(topicId);
                    // 异步保存动态
                    threadUtil.execute(new UserPubComDynamicTask(comment));
                }
            }
        } catch (Exception e) {
            log.error("CommentSev publish errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * 更新上级评论的评论数
     */
    private void parentCommentCountIncrease(String commentId) {
        if (!StringUtils.isEmpty(commentId)) {
            Comment comment = selectById(commentId);
            // 评论次数加一
            comment.setCommentCount(comment.getCommentCount() + 1);
            // 保存
            save(comment, commentId);
        }
    }
}
