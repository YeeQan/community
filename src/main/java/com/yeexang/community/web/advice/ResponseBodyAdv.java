package com.yeexang.community.web.advice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.NotificationField;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.dao.NotificationDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应处理 Advice
 *
 * @author yeeq
 * @date 2021/8/4
 */
@ControllerAdvice
public class ResponseBodyAdv implements ResponseBodyAdvice<ResponseEntity<?>> {

    @Autowired
    private NotificationDao notificationDao;

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
            QueryWrapper queryWrapper = new QueryWrapper();
            /* 用户通知处理开始 */
            queryWrapper.select("notification_type as type, count(1) as num");
            queryWrapper.eq("receiver", accountStr);
            queryWrapper.eq("status", "0");
            queryWrapper.groupBy("notification_type");
            List<Map<String , Object>> mapList = notificationDao.selectMaps(queryWrapper);
            Map<String, Integer> notificationMap = new HashMap<>();
            int sum = 0;
            int commentSum = 0;
            int likeSum = 0;
            for (Map<String, Object> map : mapList) {
                String type = map.get("type").toString();
                int num = Integer.parseInt(map.get("num").toString());
                if (NotificationField.TOPIC_VALUE.equals(type) || NotificationField.COMMENT_VALUE.equals(type)) {
                    commentSum += num;
                    notificationMap.put(NotificationField.COMMENT_LABEL, commentSum);
                } else if (NotificationField.TOPIC_LIKE_VALUE.equals(type)) {
                    likeSum += num;
                    notificationMap.put(NotificationField.TOPIC_LIKE_LABEL, likeSum);
                }
                sum += num;
            }
            notificationMap.put("all", sum);
            responseEntity.setNotificationMap(notificationMap);
            /* 用户通知处理结束 */
        }
        return responseEntity;
    }
}
