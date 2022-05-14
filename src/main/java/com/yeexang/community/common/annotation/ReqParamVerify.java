package com.yeexang.community.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验注解
 *
 * @author yeeq
 * @date 2021/12/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReqParamVerify {

    /**
     * 请求参数是否允许为空
     * @return boolean
     */
    boolean dataIsEmpty() default false;
}
