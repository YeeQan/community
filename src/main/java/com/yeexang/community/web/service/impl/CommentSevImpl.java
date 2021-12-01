package com.yeexang.community.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.CommentDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.CommentVO;
import com.yeexang.community.pojo.vo.UserVO;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.TopicSev;
import com.yeexang.community.web.service.UserSev;
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
    private UserSev userSev;

    @Autowired
    private TopicSev topicSev;

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
        List<CommentVO> commentVOList = new ArrayList<>();
        try {
            Optional<BasePO> basePOptional = commentDTO.toPO();
            if (basePOptional.isPresent()) {
                Comment comment = (Comment) basePOptional.get();
                comment.setParentId(commentDTO.getParentId());
                comment.setCommentType(CommonField.FIRST_LEVEL_COMMENT);
                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
                queryWrapper.setEntity(comment);
                List<Comment> commentList = commentDao.selectList(queryWrapper);
                commentVOList = commentList.stream()
                        .map(po -> {
                            CommentVO vo = null;
                            Optional<BaseVO> optional = po.toVO();
                            if (optional.isPresent()) {
                                vo = (CommentVO) optional.get();
                                Optional<UserVO> userOP = userSev.getUserVOByAccount(po.getCreateUser());
                                if (userOP.isPresent()) {
                                    UserVO userVO = userOP.get();
                                    vo.setCreateUsername(userVO.getUsername());
                                    vo.setHeadPortrait(userVO.getHeadPortrait());
                                }
                            }
                            return vo;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("CommentSev getFirstLevelComment errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return commentVOList;
    }

    @Override
    public List<CommentVO> getSecondLevelComment(CommentDTO commentDTO) {
        List<CommentVO> commentVOList = new ArrayList<>();
        try {
            Optional<BasePO> basePOptional = commentDTO.toPO();
            if (basePOptional.isPresent()) {
                Comment comment = (Comment) basePOptional.get();
                comment.setParentId(commentDTO.getParentId());
                comment.setCommentType(CommonField.SECOND_LEVEL_COMMENT);
                QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
                queryWrapper.setEntity(comment);
                List<Comment> commentList = commentDao.selectList(queryWrapper);
                commentVOList = commentList.stream()
                        .map(po -> {
                            CommentVO vo = null;
                            Optional<BaseVO> optional = po.toVO();
                            if (optional.isPresent()) {
                                vo = (CommentVO) optional.get();
                                Optional<UserVO> userOP = userSev.getUserVOByAccount(po.getCreateUser());
                                if (userOP.isPresent()) {
                                    UserVO userVO = userOP.get();
                                    vo.setCreateUsername(userVO.getUsername());
                                    vo.setHeadPortrait(userVO.getHeadPortrait());
                                }
                            }
                            return vo;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("CommentSev getSecondLevelComment errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return commentVOList;
    }

    @Override
    public Optional<CommentVO> publish(CommentDTO commentDTO) {
        CommentVO commentVO = null;
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
                comment.setDelFlag(false);

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
                    Optional<BaseVO> commentVOP = comment.toVO();
                    if (commentVOP.isPresent()) {
                        commentVO = (CommentVO) commentVOP.get();
                        Optional<UserVO> userOP = userSev.getUserVOByAccount(comment.getCreateUser());
                        if (userOP.isPresent()) {
                            UserVO userVO = userOP.get();
                            commentVO.setCreateUsername(userVO.getUsername());
                            commentVO.setHeadPortrait(userVO.getHeadPortrait());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("CommentSev publish errorMsg: {}", e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Optional.empty();
        }
        return Optional.ofNullable(commentVO);
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
