package com.yeexang.community.web.service.impl;

import com.yeexang.community.common.CommonField;
import com.yeexang.community.dao.CommentDao;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    @Override
    public List<Comment> getCommentList(CommentDTO commentDTO) {
        Comment commment = (Comment) commentDTO.toPO();
        List<Comment> commentList = new ArrayList<>();
        try {
            List<Comment> commentDBList = commentDao.select(commment);
            commentList.addAll(commentDBList);
        } catch (Exception e) {
            log.error("CommentSev getCommentList errorMsg: {}", e.getMessage());
        }
        return commentList;
    }

    @Override
    public List<Comment> publish(CommentDTO commentDTO, String account) {
        Comment comment = (Comment) commentDTO.toPO();
        List<Comment> commentList = new ArrayList<>();
        try {
            comment.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            int random = new Random().nextInt(1000000);
            String commentId = dateStr + random;
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
            commentList.addAll(commentDBList);

            Comment commentDB = commentDBList.get(0);
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotifier(account);

            String commentType = commentDB.getCommentType();
            String parentId = commentDB.getParentId();
            if (commentType.equals(CommonField.FIRST_LEVEL_COMMENT)) {
                Topic topicParam = new Topic();
                topicParam.setTopicId(parentId);
                Topic topic = topicDao.select(topicParam).get(0);
                notificationDTO.setReceiver(topic.getCreateUser());
                notificationDTO.setOuterId(topic.getTopicId());
                notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_TOPIC);
            } else if (commentType.equals(CommonField.SECOND_LEVEL_COMMENT)) {
                commentParam.setCommentId(parentId);
                Comment comment1 = commentDao.select(commentParam).get(0);
                notificationDTO.setReceiver(comment1.getCreateUser());
                notificationDTO.setOuterId(comment1.getCommentId());
                notificationDTO.setNotificationType(CommonField.NOTIFICATION_TYPE_COMMENT);
            }

            notificationSev.setNotify(notificationDTO);

        } catch (Exception e) {
            log.error("CommentSev publish errorMsg: {}", e.getMessage());
        }
        return commentList;
    }
}
