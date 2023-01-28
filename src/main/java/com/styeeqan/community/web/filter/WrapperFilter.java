package com.styeeqan.community.web.filter;

import com.styeeqan.community.common.http.request.RequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/**")
public class WrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 防止流读取一次就没了,将流继续传递下去
        filterChain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
    }
}
