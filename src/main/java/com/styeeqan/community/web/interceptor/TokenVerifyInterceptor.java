package com.styeeqan.community.web.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.styeeqan.community.common.constant.CommonField;
import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.exception.CustomizeException;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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

    @Autowired
    private RedisUtil redisUtil;

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

        if (StringUtils.isEmpty(token)) {
            throw new CustomizeException(ServerStatusCode.UNAUTHORIZED);
        }

        // 校验 token
        Optional<DecodedJWT> optional = jwtUtil.getTokenInfo(token);
        if (optional.isPresent()) {
            DecodedJWT decodedJWT = optional.get();
            if (decodedJWT.getClaim(CommonField.ACCOUNT) != null) {
                String account = decodedJWT.getClaim(CommonField.ACCOUNT).asString();
                // 校验 account 是否合法
                Optional<String> tokenOpt = redisUtil.getValue(RedisKey.USER_TOKEN, account);
                if (tokenOpt.isPresent() && tokenOpt.get().equals(token)) {
                    // token 续命
                    redisUtil.setValue(RedisKey.USER_TOKEN, account, token);
                    // 将 token 中的 account 放到 request 里面，转发到业务
                    request.setAttribute(CommonField.ACCOUNT, account);
                } else {
                    redisUtil.delete(RedisKey.USER_TOKEN, account);
                    throw new CustomizeException(ServerStatusCode.UNAUTHORIZED);
                }
            }
        } else {
            throw new CustomizeException(ServerStatusCode.UNAUTHORIZED);
        }

        return true;
    }
}
