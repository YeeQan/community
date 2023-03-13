package com.styeeqan.community.aspect;

import com.alibaba.fastjson.JSON;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.common.util.HttpUtil;
import com.styeeqan.community.common.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Controller日志切面
 *
 * @author yeeq
 * @date 2021/8/4
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class ControllerLogAsp {

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    private HttpUtil httpUtil;

    @Pointcut("execution(* com.styeeqan.community.web.controller.*.*(..))")
    public void controllerMethod() {}

    /**
     * 执行 Controller 方法打印日志
     */
    @Around(value = "controllerMethod()")
    public Object LogRequestInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        // 持有上下文的 request 容器
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = ipUtil.getRealIp(request);
        String uri = request.getRequestURI();
        String method = request.getMethod();
        Optional<String> jsonDataOp = httpUtil.getJsonData(request);
        String jsonData = jsonDataOp.orElse(null);
        long startTime = System.currentTimeMillis();
        log.info("开始执行请求, method:{}, ip:{}, uri:{}, request-datas:{}",
                method, ip, uri, StringUtils.isEmpty(jsonData) ? "no-datas" : JSON.toJSONString(jsonData).replaceAll("\\\\", ""));
        Object result = joinPoint.proceed();
        log.info("请求执行结束, spent-time:{}ms, method:{}, ip:{}, uri:{}, response-datas:{}",
                System.currentTimeMillis() - startTime, method, ip, uri,
                result == null ? "no-datas" : JSON.toJSONString(result));
        return result;
    }
}
