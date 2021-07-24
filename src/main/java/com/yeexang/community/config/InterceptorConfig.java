package com.yeexang.community.config;

import com.yeexang.community.web.interceptor.TokenVerifyInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yeeq
 * @date 2021/7/23
 */
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token 校验拦截器
        registry.addInterceptor(new TokenVerifyInterceptor());
    }
}
