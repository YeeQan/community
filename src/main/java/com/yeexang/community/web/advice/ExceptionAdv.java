package com.yeexang.community.web.advice;

import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理 Advice
 *
 * @author yeeq
 * @date 2021/8/12
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdv {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> exceptionHandler(Exception e) {
        log.error("ExceptionAdv exceptionHandler errorMsg: {}", e.getMessage(), e);
        return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
    }
}
