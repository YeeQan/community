package com.yeexang.community.web.advice;

import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.dto.NotificationDTO;
import com.yeexang.community.pojo.po.Notification;
import com.yeexang.community.web.service.NotificationSev;
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

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public ResponseEntity<?> beforeBodyWrite(ResponseEntity<?> responseEntity, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest sshr = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest request = sshr.getServletRequest();
        String account = request.getAttribute("account").toString();
        if (!StringUtils.isEmpty(account)) {
            NotificationDTO param = new NotificationDTO();
            param.setReceiver(account);
            List<Notification> notificationList = notificationSev.receive(param);
            List<NotificationDTO> notificationDTOList = notificationList.stream()
                    .map(notification -> (NotificationDTO) notification.toDTO())
                    .collect(Collectors.toList());
            responseEntity.setNotificationDTOList(notificationDTOList);
        }
        return responseEntity;
    }
}