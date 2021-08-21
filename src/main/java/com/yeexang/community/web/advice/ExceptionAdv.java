package com.yeexang.community.web.advice;

import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yeeq
 * @date 2021/8/12
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdv {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> exceptionHandler(Exception e) {
        log.error("ExceptionAdv exceptionHandler Exception: {}, errorMsg: {}", e.toString(), e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(ServerStatusCode.UNKNOWN);
    }
}
