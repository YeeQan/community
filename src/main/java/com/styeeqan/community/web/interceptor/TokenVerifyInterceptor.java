package com.styeeqan.community.web.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.meta.When;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * token 拦截器
 *
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@Component
public class TokenVerifyInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * token 校验白名单
     */
    private final List<String> WHITE_URI_LIST = Arrays.asList(
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
            "/topic/page",
            "/topic/visit",
            "/topic/view/**",
            "/common/header-logined",
            "/common/header-non-logined",
            "/common/footer",
            "/topic/view/**",
            "/u/**",
            "/user/homepage",
            "/user/dynamic/list"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (CommonField.TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // 如果在白名单,解析 token 直接返回
        String requestURI = request.getRequestURI();
        for (String whiteUri : WHITE_URI_LIST) {
            if (requestURI.contains(whiteUri)) {
                if (!StringUtils.isEmpty(token)) {
                    Optional<DecodedJWT> optional = jwtUtil.getTokenInfo(token);
                    if (optional.isPresent()) {
                        DecodedJWT decodedJWT = optional.get();
                        String account = decodedJWT.getClaim(CommonField.ACCOUNT).asString();
                        // 将 token 中的 account 放到 request 里面，转发到业务
                        request.setAttribute(CommonField.ACCOUNT, account);
                    }
                }
                return true;
            }
        }

        if (StringUtils.isEmpty(token)) {
            throw new CustomizeException(ServerStatusCode.UNAUTHORIZED);
        }

        // 校验 token
        Optional<DecodedJWT> optional = jwtUtil.getTokenInfo(token);
        if (optional.isPresent()) {
            DecodedJWT decodedJWT = optional.get();
            String account = decodedJWT.getClaim(CommonField.ACCOUNT).asString();
            // 将 token 中的 account 放到 request 里面，转发到业务
            request.setAttribute(CommonField.ACCOUNT, account);
        } else {
            throw new CustomizeException(ServerStatusCode.UNAUTHORIZED);
        }

        return true;
    }
}
