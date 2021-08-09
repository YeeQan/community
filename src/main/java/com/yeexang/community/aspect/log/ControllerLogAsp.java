package com.yeexang.community.aspect.log;

import com.yeexang.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author yeeq
 * @date 2021/8/4
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAsp {

    @Pointcut("execution(* com.yeexang.community.web.controller.*.*(..))")
    public void controllerMethod() {

    }

    @Before("controllerMethod()")
    public void LogRequestInfo(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String typeName = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append(typeName).append("controller log start: ")
                .append(typeName).append(" ")
                .append(methodName).append("(");
        for (int i = 0; i < args.length; i++) {
            if (i == args.length - 1) {
                sb.append(args[i].toString()).append(")");
            } else {
                sb.append(args[i].toString()).append(",");
            }
        }
        log.info(sb.toString());
    }

    @AfterReturning(returning = "responseEntity", pointcut = "controllerMethod()", argNames = "responseEntity,joinPoint")
    public void logResultVOInfo(ResponseEntity responseEntity, JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String typeName = signature.getDeclaringTypeName();
        StringBuilder sb = new StringBuilder();
        sb.append(typeName).append("controller log end: ")
                .append(typeName).append(" ")
                .append(methodName).append(" ")
                .append("returnValue: ").append(responseEntity.toString());
        log.info(sb.toString());
    }
}
