package com.styeeqan.community.common.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

/**
 * Cookie 工具类
 *
 * @author yeeq
 * @date 2021/7/24
 */
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
    public Cookie getCookie(String key, String value, int time) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(false);
        cookie.setMaxAge(time);
        cookie.setPath("/");
        cookie.setSecure(false);
        return cookie;
    }
}
