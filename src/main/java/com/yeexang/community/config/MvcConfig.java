package com.yeexang.community.config;

import com.yeexang.community.web.interceptor.RateLimiterInterceptor;
import com.yeexang.community.web.interceptor.TokenVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token 校验拦截器
        registry.addInterceptor(getTokenVerifyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/bootstrap-4.6.0/**",
                        "/editor.md/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/",
                        "/index",
                        "/user/login",
                        "/user/register",
                        "/user/person",
                        "/topic/page",
                        "/comment/first/list",
                        "/comment/second/list",
                        "/section/list",
                        "/topic/visit",
                        "/common/header-logined",
                        "/common/header-non-logined",
                        "/common/footer",
                        "/topic/view/**",
                        "/homepage/**");
        // RateLimiter 限流拦截器
        registry.addInterceptor(getRateLimiterInterceptor()).addPathPatterns("/**");
    }
}
