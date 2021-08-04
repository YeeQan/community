package com.yeexang.community.web.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Slf4j
@Component
public class TokenVerifyInterceptor implements HandlerInterceptor {

    @Value("#{'${interceptor.token-white-request-uri:}'.empty ? null : '${interceptor.token-white-request-uri:}'.split(',')}")
    private List<String> tokenWhiteRequestUris;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("TokenVerifyInterceptor preHandle start --------------------------");

        // 获取请求的 URI 路径
        String requestUri = request.getRequestURI();
        // 如果请求的 URI 在白名单中，则跳过 token 验证
        if (tokenWhiteRequestUris != null && tokenWhiteRequestUris.contains(requestUri)) {
            return true;
        }

        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (CommonField.TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        try {
            // 校验 token
            jwtUtil.verifyJwt(token);
            // 从加密 token 中获取信息
            DecodedJWT tokenInfo = jwtUtil.getTokenInfo(token);
            String account = tokenInfo.getClaim("account").asString();
            // 将 token 中的 account 放到 request 里面，转发到业务
            request.setAttribute("account", account);
        } catch (Exception e) {
            // 验证失败，返回 401 状态码
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
