package com.styeeqan.community.web.filter;

import com.styeeqan.community.common.http.request.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(urlPatterns = "/**")
public class WrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        HttpServletRequest requestWrapper = null;
        try {
            // 防止流读取一次就没了,将流继续传递下去
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
            filterChain.doFilter(requestWrapper, response);
        } catch (Exception e) {
            log.error("[WrapperFilter-Error] uri:{}", requestWrapper.getRequestURI(), e);
        }
    }
}
