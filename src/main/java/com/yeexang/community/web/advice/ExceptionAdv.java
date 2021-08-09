package com.yeexang.community.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yeeq
 * @date 2021/8/5
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdv implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        log.error("ExceptionAdv resolveException: {}", e.getMessage());
        mv.setViewName("error");
        return mv;
    }
}
