package com.yeexang.community.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeexang.community.common.constant.NotificationField;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.dao.CommentDao;
import com.yeexang.community.dao.NotificationDao;
import com.yeexang.community.dao.TopicDao;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.NotificationVO;
import com.yeexang.community.pojo.vo.PageVO;
import com.yeexang.community.web.service.base.BaseSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2022/4/8
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationSev extends BaseSev<Notification, String> {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private UserSev userSev;

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private CommentSev commentSev;

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.NOTIFICATION;
    }

    @Override
    public BaseMapper<Notification> getBaseMapper() {
        return notificationDao;
    }

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }

    /**
     * 接收消息
     *
     * @param notification notification
     */
    public void receive(Notification notification) {
        try {
            String type = notification.getNotificationType();
            String outerId = notification.getOuterId();
            if (NotificationField.TOPIC_VALUE.equals(type) ||
                    NotificationField.TOPIC_LIKE_VALUE.equals(type)) {
                Topic topic = topicDao.selectById(outerId);
                if (topic != null) {
                    notification.setReceiver(topic.getCreateUser());
                }
            } else if (NotificationField.COMMENT_VALUE.equals(type)) {
                Comment comment = commentDao.selectById(outerId);
                if (comment != null) {
                    notification.setReceiver(comment.getCreateUser());
                }
            }
            save(notification, notification.getNotificationId());
        } catch (Exception e) {
            log.error("NotificationSev receive errorMsg: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取通知信息
     *
     * @param pageNum  pageNum
     * @param pageSize pageSize
     * @param receiver receiver
     * @param types types
     * @return PageVO<NotificationVO>
     */
    public PageVO<NotificationVO> getNotificationList(Integer pageNum, Integer pageSize, String receiver, List<String> types) {
        PageVO<NotificationVO> pageVO = new PageVO<>();
        try {
            QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("receiver", receiver);
            queryWrapper.in("notification_type", types);
            queryWrapper.orderByDesc("update_time");
            if (pageNum > 0 && pageSize > 0) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<Notification> notificationList = notificationDao.selectList(queryWrapper);
            PageInfo<Notification> pageInfo = new PageInfo<>(notificationList);

            pageVO.setPageNum(pageInfo.getPageNum());
            pageVO.setPages(pageInfo.getPages());
            pageVO.setNavigatepageNums(pageInfo.getNavigatepageNums());

            List<NotificationVO> notificationVOList = notificationList.stream()
                    .map(po -> {
                        NotificationVO vo = null;
                        Optional<BaseVO> baseVOptional = po.toVO();
                        if (baseVOptional.isPresent()) {
                            vo = (NotificationVO) baseVOptional.get();
                            // 查找通知者用户信息
                            User notifierUser = userSev.selectById(po.getNotifier());
                            vo.setNotifierName(notifierUser.getUsername());
                            // 查找接收者用户信息
                            User receiverUser = userSev.selectById(po.getReceiver());
                            vo.setReceiverHeadPortrait(receiverUser.getHeadPortrait());
                            // 查找外部和内部信息
                            if (NotificationField.TOPIC_VALUE.equals(po.getNotificationType())) {
                                Topic outer = topicSev.selectById(po.getOuterId());
                                vo.setOuterName(outer.getTopicTitle());
                                Comment inner = commentSev.selectById(po.getInnerId());
                                vo.setInnerName(inner.getCommentContent());
                            } else if (NotificationField.COMMENT_VALUE.equals(po.getNotificationType())) {
                                Comment outer = commentSev.selectById(po.getOuterId());
                                vo.setOuterName(outer.getCommentContent());
                                Comment inner = commentSev.selectById(po.getInnerId());
                                String innerName = inner.getCommentContent();
                                if (innerName.length() > 15) {
                                    // 截取前15个字符
                                    innerName = innerName.substring(0, 15) + "...";
                                }
                                vo.setInnerName(innerName);
                            } else if (NotificationField.TOPIC_LIKE_VALUE.equals(po.getNotificationType())) {
                                Topic outer = topicSev.selectById(po.getOuterId());
                                vo.setOuterName(outer.getTopicTitle());
                            }
                        }
                        return vo;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            pageVO.setList(notificationVOList);
        } catch (Exception e) {
            log.error("NotificationSev getNotificationList errorMsg: {}", e.getMessage(), e);
            return new PageVO<>();
        }
        return pageVO;
    }
}
