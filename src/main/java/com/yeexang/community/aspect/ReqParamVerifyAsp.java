package com.yeexang.community.aspect;

import com.yeexang.community.common.annotation.ReqParamVerify;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.request.RequestEntity;
import com.yeexang.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Controller 参数校验切面
 *
 * @author yeeq
 * @date 2021/8/4
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class ReqParamVerifyAsp {

    @Pointcut("execution(* com.yeexang.community.web.controller.*.*(..))")
    public void controllerMethod() {

    }

    /**
     * 执行 Controller 方法前校验参数
     * @param joinPoint joinPoint
     */
    @Around(value = "controllerMethod()", argNames = "joinPoint")
    public Object paramVerify(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        ReqParamVerify reqParamVerify = method.getAnnotation(ReqParamVerify.class);
        // 根据注解校验参数
        if (reqParamVerify != null) {
            RequestEntity requestEntity = null;
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof RequestEntity) {
                    requestEntity = (RequestEntity) arg;
                }
            }
            if (requestEntity != null) {
                // 校验参数是否为空
                if (!reqParamVerify.dataIsEmpty()) {
                    if (requestEntity.getData() == null || requestEntity.getData().isEmpty()
                            || requestEntity.getData().get(0) == null) {
                        return new ResponseEntity<>(ServerStatusCode.REQUEST_DATA_EMPTY);
                    }
                }
            }
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }
}