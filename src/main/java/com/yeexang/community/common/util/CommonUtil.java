package com.yeexang.community.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/8/9
 */
@Component
public class CommonUtil {

    /**
     * 生成 32 位随机数
     * @return String
     */
    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
