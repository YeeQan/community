package com.yeexang.community.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.yeexang.community.common.annotation.RateLimiterAnnotation;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * RateLimiter 拦截器
 *
 * @author yeeq
 * @date 2021/12/16
 */
@Slf4j
@Component
    public class RateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterUtil rateLimiterUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PrintWriter out;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RateLimiterAnnotation rateLimiterAnnotation = handlerMethod.getMethod().getAnnotation(RateLimiterAnnotation.class);
            if(rateLimiterAnnotation == null) {
                return true;
            }

            Object attribute = request.getAttribute(CommonField.ACCOUNT);

            if (attribute == null) {
                return true;
            } else {
                String account = attribute.toString();
                boolean acquire = rateLimiterUtil.tryAcquire(account, rateLimiterAnnotation.permitsPerSecond());
                if (!acquire) {
                    ResponseEntity<?> responseEntity = new ResponseEntity<>(ServerStatusCode.USER_VISIT_FREQUENTLY);
                    String json = JSON.toJSONString(responseEntity);
                    out = response.getWriter();
                    out.append(json);
                    return false;
                }
            }
        } catch (Exception e) {
            log.error("RateLimiterInterceptor preHandle errorMsg: {}", e.getMessage(), e);
            ResponseEntity<?> responseEntity = new ResponseEntity<>(ServerStatusCode.UNKNOWN);
            String json = JSON.toJSONString(responseEntity);
            out = response.getWriter();
            out.append(json);
            return false;
        }
        return true;
    }
}
