package com.yeexang.community.web.advice;

import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.*;
import com.yeexang.community.pojo.po.Comment;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.pojo.po.Topic;
import com.yeexang.community.pojo.po.User;
import com.yeexang.community.web.service.CommentSev;
import com.yeexang.community.web.service.NotificationSev;
import com.yeexang.community.web.service.TopicSev;
import com.yeexang.community.web.service.UserSev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 响应处理 Advice
 *
 * @author yeeq
 * @date 2021/8/4
 */
@ControllerAdvice
public class ResponseBodyAdv implements ResponseBodyAdvice<ResponseEntity<?>> {

    @Autowired
    private NotificationSev notificationSev;

    @Autowired
    private UserSev userSev;

    @Autowired
    private TopicSev topicSev;

    @Autowired
    private CommentSev commentSev;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public ResponseEntity<?> beforeBodyWrite(ResponseEntity<?> responseEntity, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest sshr = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest request = sshr.getServletRequest();
        Object account = request.getAttribute(CommonField.ACCOUNT);
        if (account != null && !StringUtils.isEmpty(account.toString())) {
            String accountStr = account.toString();
            /* 用户通知处理 */
            NotificationDTO param = new NotificationDTO();
            param.setReceiver(accountStr);
            List<Notification> notificationList = notificationSev.receive(param);
            List<NotificationDTO> notificationDTOList = notificationList.stream()
                    .map(notification -> {
                        NotificationDTO notificationDTO = null;
                        Optional<BaseDTO> dtoOptional = notification.toDTO();
                        if (dtoOptional.isPresent()) {
                            notificationDTO = (NotificationDTO) dtoOptional.get();
                            UserDTO userDTO = new UserDTO();
                            // 设置通知者用户名
                            userDTO.setAccount(notificationDTO.getNotifier());
                            List<User> notifierList = userSev.getUser(userDTO);
                            if (!notifierList.isEmpty()) {
                                String notifierName = notifierList.get(0).getUsername();
                                notificationDTO.setNotifierName(notifierName);
                            }
                            // 设置接收者用户名
                            userDTO.setAccount(notificationDTO.getReceiver());
                            List<User> receiverList = userSev.getUser(userDTO);
                            if (!receiverList.isEmpty()) {
                                String receiverName = receiverList.get(0).getUsername();
                                notificationDTO.setReceiverName(receiverName);
                            }
                            // 根据评论类型设置信息
                            String type = notificationDTO.getNotificationType();
                            if (!StringUtils.isEmpty(type)) {
                                if (type.equals(CommonField.NOTIFICATION_TYPE_TOPIC)
                                        || type.equals(CommonField.NOTIFICATION_TYPE_LIKE_TOPIC)) {
                                    TopicDTO topicDTO = new TopicDTO();
                                    topicDTO.setTopicId(notificationDTO.getOuterId());
                                    List<Topic> topicList = topicSev.getTopic(topicDTO);
                                    if (!topicList.isEmpty()) {
                                        String title = topicList.get(0).getTopicTitle();
                                        notificationDTO.setOuterName(title);
                                    }
                                } else if (StringUtils.isEmpty(type)
                                        || type.equals(CommonField.NOTIFICATION_TYPE_COMMENT)
                                        || type.equals(CommonField.NOTIFICATION_TYPE_LIKE_COMMENT)) {
                                    CommentDTO commentDTO = new CommentDTO();
                                    commentDTO.setCommentId(notificationDTO.getOuterId());
                                    List<Comment> commentList = commentSev.getCommentList(commentDTO);
                                    if (!commentList.isEmpty()) {
                                        String content = commentList.get(0).getCommentContent();
                                        notificationDTO.setOuterName(content);
                                    }
                                }
                            }
                        }
                        return notificationDTO;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            responseEntity.setNotificationDTOList(notificationDTOList);
        }
        return responseEntity;
    }
}