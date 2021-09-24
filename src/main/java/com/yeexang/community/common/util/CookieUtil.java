package com.yeexang.community.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Optional;

/**
 * Cookie 工具类
 *
 * @author yeeq
 * @date 2021/7/24
 */
@Slf4j
@Component
public class CookieUtil {

    /**
     * 获取 Cookie
     *
     * @param key   cookie 键
     * @param value cookie 值
     * @param time  过期时间
     * @return {@link Cookie}
     */
    public Optional<Cookie> getCookie(String key, String value, int time) {
        Cookie cookie;
        try {
            cookie = new Cookie(key, value);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(time);
            cookie.setPath("/");
            cookie.setSecure(false);
        } catch (Exception e) {
            log.error("CookieUtil getCookie errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(cookie);
    }
}
