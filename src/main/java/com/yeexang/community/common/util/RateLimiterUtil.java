package com.yeexang.community.common.util;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * RateLimiter 限流器工具类
 *
 * @author yeeq
 * @date 2021/12/16
 */
@Slf4j
@Component
public class RateLimiterUtil {

    /**
     * 保存用户的唯一标识id
     */
    private final static Map<String, RateLimiter> USER_LIMIT_MAP = new ConcurrentHashMap<>();

    /**
     * 获取令牌
     */
    public boolean tryAcquire(@NonNull String account, @NonNull double permitsPerSecond) {
        boolean flag;
        try {
            RateLimiter rateLimiter;
            if (USER_LIMIT_MAP.containsKey(account)) {
                rateLimiter = USER_LIMIT_MAP.get(account);
            } else {
                // 一秒内1个令牌
                rateLimiter = RateLimiter.create(permitsPerSecond);
                USER_LIMIT_MAP.put(account, rateLimiter);
            }
            flag = rateLimiter.tryAcquire();
        } catch (Exception e) {
            log.error("User: {} RateLimiterUtil tryAcquire error: {}", account, e.getMessage(), e);
            return false;
        }
        return flag;
    }
}
