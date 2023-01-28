package com.styeeqan.community.web.interceptor;

import com.styeeqan.community.common.annotation.RateLimiterAnnotation;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.util.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

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
                log.error("User:{}  uri:{} visit frequently", account, request.getRequestURI());
                throw new CustomizeException(ServerStatusCode.USER_VISIT_FREQUENTLY);
            }
        }

        return true;
    }
}
