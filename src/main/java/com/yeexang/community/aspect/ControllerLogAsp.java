package com.yeexang.community.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Controller 日志切面
 *
 * @author yeeq
 * @date 2021/8/4
 */
@Slf4j
@Aspect
@Component
@Order(2)
public class ControllerLogAsp {

    @Pointcut("execution(* com.yeexang.community.web.controller.*.*(..))")
    public void controllerMethod() {

    }

    /**
     * 执行 Controller 方法前打印日志
     * @param joinPoint joinPoint
     */
    @Before(value = "controllerMethod()", argNames = "joinPoint")
    public void LogRequestInfo(JoinPoint joinPoint) {
        // 持有上下文的 request 容器
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 获取 request 请求
            HttpServletRequest request = attributes.getRequest();
            // 获取用户账户号
            Object attribute = request.getAttribute(CommonField.ACCOUNT);
            if (attribute == null) {
                // 如果账户为空，则不记录日志
                return;
            }
            String account = attribute.toString();
            // 获取请求 uri
            String requestURI = request.getRequestURI();
            // 获取方法签名
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            // 获取方法
            Method method = methodSignature.getMethod();
            // 获取方法上面的注解
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            // 根据注解类型打印日志
            if (getMapping != null) {
                log.info("user:{} getRequest send:{}", account, requestURI);
            }
            if (postMapping != null) {
                // 方法参数
                RequestEntity requestEntity = null;
                for (Object arg : joinPoint.getArgs()) {
                    if (arg instanceof RequestEntity) {
                        requestEntity = (RequestEntity) arg;
                    }
                }
                log.info("user:{} postRequest send:{}, requestParam: {}",
                        account, requestURI, requestEntity == null ? "null" : JSON.toJSONString(requestEntity));
            }
        }
    }

    /**
     * Controller 方法执行完毕打印日志
     * @param joinPoint joinPoint
     * @param responseEntity 响应
     */
    @AfterReturning(returning = "responseEntity", pointcut = "controllerMethod()")
    public void logResultInfo(JoinPoint joinPoint, ResponseEntity responseEntity) {
        // 持有上下文的 request 容器
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 获取 request 请求
            HttpServletRequest request = attributes.getRequest();
            // 获取用户账户号
            Object attribute = request.getAttribute(CommonField.ACCOUNT);
            if (attribute == null) {
                // 如果账户为空，则不记录日志
                return;
            }
            String account = attribute.toString();
            // 获取请求 uri
            String requestURI = request.getRequestURI();
            // 获取方法签名
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            // 获取方法
            Method method = methodSignature.getMethod();
            // 获取方法上面的注解
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            // 根据注解类型打印日志
            if (postMapping != null) {
                log.info("user:{} postRequest finish:{}, result: {}",
                        account, requestURI, responseEntity);
            }
        }
    }
}