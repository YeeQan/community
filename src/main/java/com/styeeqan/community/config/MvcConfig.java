package com.styeeqan.community.config;

import com.styeeqan.community.web.interceptor.DecryptInterceptor;
import com.styeeqan.community.web.interceptor.RateLimiterInterceptor;
import com.styeeqan.community.web.interceptor.TokenVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置类
 *
 * @author yeeq
 * @date 2021/7/23
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getTokenVerifyInterceptor() {
        return new TokenVerifyInterceptor();
    }

    @Bean
    public HandlerInterceptor getRateLimiterInterceptor() {
        return new RateLimiterInterceptor();
    }

    @Bean
    public HandlerInterceptor getDecryptInterceptor() {
        return new DecryptInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 解密拦截器
        registry.addInterceptor(getDecryptInterceptor()).addPathPatterns("/**");
        // token 校验拦截器
        registry.addInterceptor(getTokenVerifyInterceptor()).addPathPatterns("/**");
        // RateLimiter 限流拦截器
        // registry.addInterceptor(getRateLimiterInterceptor()).addPathPatterns("/**");
    }
}
