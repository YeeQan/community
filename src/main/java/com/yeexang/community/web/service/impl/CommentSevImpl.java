package com.yeexang.community.web.service.impl;

import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.util.CommonUtil;
import com.yeexang.community.dao.CommentDao;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@Service
public class CommentSevImpl implements CommentSev {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private NotificationSev notificationSev;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public List<Comment> getCommentList(CommentDTO commentDTO) {
        List<Comment> commentList = new ArrayList<>();
        try {
            Optional<BasePO> optional = commentDTO.toPO();
            if (optional.isPresent()) {
                Comment comment = (Comment) optional.get();
                List<Comment> commentDBList = commentDao.select(comment);
                if (!commentDBList.isEmpty()) {
                    commentList.addAll(commentDBList);
                }
            }
        } catch (Exception e) {
            log.error("CommentSev getCommentList errorMsg: {}", e.getMessage());
            return new ArrayList<>();
        }
        return commentList;
    }

    @Override
    public List<Comment> publish(CommentDTO commentDTO, String account) {
        List<Comment> commentList = new ArrayList<>();
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
                comment.setCreateUser(account);
                comment.setUpdateTime(new Date());
                comment.setUpdateUser(account);
                comment.setDelFlag(false);
                commentDao.insert(comment);

                Comment commentParam = new Comment();
                commentParam.setCommentId(commentId);
                List<Comment> commentDBList = commentDao.select(commentParam);
                if (!commentDBList.isEmpty()) {
                    commentList.addAll(commentDBList);

                    Comment commentDB = commentDBList.get(0);
                    NotificationDTO notificationDTO = new NotificationDTO();
                    notificationDTO.setNotifier(account);
                    String commentType = commentDB.getCommentType();
                    String parentId = commentDB.getParentId();
                    Topic topicParam = new Topic();

                    if (commentType.equals(CommonField.FIRST_LEVEL_COMMENT)) {
                        topicParam.setTopicId(parentId);
                        List<Topic> topicList = topicDao.select(topicParam);
                        if (!topicList.isEmpty()) {
                            Topic topic = topicList.get(0);
                            topicDao.updateCommentCountIncrease(topic.getTopicId());
                            notificationDTO.setReceiver(topic.getCreateUser());
                            notificationDTO.setOuterId(topic.getTopicId());
                            notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_TOPIC);
                        }
                    } else if (commentType.equals(CommonField.SECOND_LEVEL_COMMENT)) {
                        commentParam.setCommentId(parentId);
                        List<Comment> commentParentList = commentDao.select(commentParam);
                        if (!commentParentList.isEmpty()) {
                            Comment commentParent = commentParentList.get(0);
                            commentDao.updateCommentCountIncrease(commentParent.getCommentId());
                            topicParam.setTopicId(commentParam.getParentId());
                            List<Topic> topicList = topicDao.select(topicParam);
                            if (!topicList.isEmpty()) {
                                Topic topic = topicDao.select(topicParam).get(0);
                                topicDao.updateCommentCountIncrease(topic.getTopicId());
                            }
                            notificationDTO.setReceiver(commentParent.getCreateUser());
                            notificationDTO.setOuterId(commentParent.getCommentId());
                            notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_COMMENT);
                        }
                    }
                    notificationSev.setNotify(notificationDTO);
                }
            }
        } catch (Exception e) {
            log.error("CommentSev publish errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return commentList;
    }

    @Override
    public List<Comment> like(CommentDTO commentDTO, String account) {
        List<Comment> commentList = new ArrayList<>();
        try {
            String commentId = commentDTO.getCommentId();
            commentDao.updateLikeCountIncrease(commentId);

            Comment commentParam = new Comment();
            commentParam.setCommentId(commentId);
            List<Comment> commentDBList = commentDao.select(commentParam);
            if (!commentDBList.isEmpty()) {
                commentList.addAll(commentDBList);

                Comment commentDB = commentDBList.get(0);
                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setNotifier(account);
                notificationDTO.setReceiver(commentDB.getCreateUser());
                notificationDTO.setOuterId(commentDB.getCommentId());
                notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_LIKE_COMMENT);
                notificationSev.setNotify(notificationDTO);
            }
        } catch (Exception e) {
            log.error("CommentSev like errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return commentList;
    }
}
