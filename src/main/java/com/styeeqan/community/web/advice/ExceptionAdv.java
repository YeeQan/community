package com.styeeqan.community.web.advice;

import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.http.response.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author yeeq
 * @date 2021/8/12
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdv {

    /**
     * 自定义异常
     */
    @ResponseBody
    @ExceptionHandler(value = CustomizeException.class)
    public ResponseEntity<?> handleCustomizeException(CustomizeException e) {
        log.error("自定义异常: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getError());
    }

    /**
     * 参数绑定异常
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleValidException(BindException e) {
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数绑定异常
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidException(MethodArgumentNotValidException e) {
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        log.error("未知异常: {}", e.getMessage(), e);
        return "error";
    }

    /**
     * 包装参数绑定异常提示信息
     */
    private ResponseEntity<?> wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage()).append(";&nbsp;");
        }
        ResponseEntity<?> response = new ResponseEntity<>(ServerStatusCode.PARAM_ERROR);
        response.setDescription(msg.toString());
        return response;
    }
}
