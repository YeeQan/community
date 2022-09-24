package com.styeeqan.community.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流注解
 *
 * @author yeeq
 * @date 2021/12/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiterAnnotation {

    /**
     * 每秒产生令牌数
     */
    double permitsPerSecond ();
}
