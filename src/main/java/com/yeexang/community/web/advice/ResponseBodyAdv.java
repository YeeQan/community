package com.yeexang.community.web.advice;

import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.CommentDTO;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.dto.TopicDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.po.Notification;
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
import java.util.stream.Collectors;

/**
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
        Object account = request.getAttribute("account");
        if (account != null && !StringUtils.isEmpty(account.toString())) {
            String accountStr = account.toString();
            NotificationDTO param = new NotificationDTO();
            param.setReceiver(accountStr);
            List<Notification> notificationList = notificationSev.receive(param);
            List<NotificationDTO> notificationDTOList = notificationList.stream()
                    .map(notification -> {
                        NotificationDTO dto = (NotificationDTO) notification.toDTO();
                        UserDTO userDTO = new UserDTO();
                        userDTO.setAccount(param.getNotifier());
                        String notifierName = userSev.getUser(userDTO).get(0).getUsername();
                        dto.setNotifierName(notifierName);
                        userDTO.setAccount(param.getReceiver());
                        String receiverName = userSev.getUser(userDTO).get(0).getUsername();
                        dto.setReceiverName(receiverName);
                        String type = dto.getNotificationType();
                        if (type.equals(CommonField.NOTIFICATION_TYPE_TOPIC)
                                || type.equals(CommonField.NOTIFICATION_TYPE_LIKE_TOPIC)) {
                            TopicDTO topicDTO = new TopicDTO();
                            topicDTO.setTopicId(dto.getOuterId());
                            String title = topicSev.getTopic(topicDTO).get(0).getTopicTitle();
                            dto.setOuterName(title);
                        } else if (type.equals(CommonField.NOTIFICATION_TYPE_COMMENT)
                                || type.equals(CommonField.NOTIFICATION_TYPE_LIKE_COMMENT)) {
                            CommentDTO commentDTO = new CommentDTO();
                            commentDTO.setCommentId(dto.getOuterId());
                            String content = commentSev.getCommentList(commentDTO).get(0).getCommentContent();
                            dto.setOuterName(content);
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
            responseEntity.setNotificationDTOList(notificationDTOList);
        }
        return responseEntity;
    }
}