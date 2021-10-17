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
 * Controller 日志切面
 *
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

    /**
     * 执行 Controller 方法前打印日志
     * @param joinPoint joinPoint
     */
    @Before(value = "controllerMethod()", argNames = "joinPoint")
    public void LogRequestInfo(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        // 方法名
        String methodName = signature.getName();
        // 类名
        String typeName = signature.getDeclaringTypeName();
        // 方法参数
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append("controller log start: ").append(typeName).append(" ")
                .append(methodName).append("(");
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                if (i == args.length - 1) {
                    sb.append(args[i].toString()).append(")");
                } else {
                    sb.append(args[i].toString()).append(",");
                }
            } else {
                if (i == args.length - 1) {
                    sb.append("null").append(")");
                } else {
                    sb.append("null").append(",");
                }
            }
        }
        log.info(sb.toString());
    }

    /**
     * Controller 方法执行完毕打印日志
     * @param joinPoint joinPoint
     * @param responseEntity 响应
     */
    @AfterReturning(returning = "responseEntity", pointcut = "controllerMethod()")
    public void logResultInfo(JoinPoint joinPoint, ResponseEntity responseEntity) {
        Signature signature = joinPoint.getSignature();
        // 方法名
        String methodName = signature.getName();
        // 类名
        String typeName = signature.getDeclaringTypeName();
        // 拼接返回值
        String sb = typeName + "controller log end: " +
                typeName + " " +
                methodName + " " +
                "returnValue: " + responseEntity.toString();
        log.info(sb);
    }
}