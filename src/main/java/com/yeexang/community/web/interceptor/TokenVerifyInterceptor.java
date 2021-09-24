package com.yeexang.community.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yeexang.community.common.CommonField;
import com.yeexang.community.common.ServerStatusCode;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

    /**
     * token 校验白名单
     */
    @Value("#{'${interceptor.token-white-request-uri:}'.empty ? null : '${interceptor.token-white-request-uri:}'.split(',')}")
    private List<String> tokenWhiteRequestUris;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("TokenVerifyInterceptor preHandle start");

        PrintWriter out;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            // 获取请求的 URI 路径
            String requestUri = request.getRequestURI();
            // 如果请求的 URI 在白名单中，则跳过 token 验证
            if (tokenWhiteRequestUris != null && !tokenWhiteRequestUris.isEmpty()) {
                for (String whiteRequestUri : tokenWhiteRequestUris) {
                    if (requestUri.equals(whiteRequestUri)) {
                        return true;
                    }
                }
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
                ResponseEntity<?> responseEntity = new ResponseEntity<>(ServerStatusCode.UNAUTHORIZED);
                String json = JSON.toJSONString(responseEntity);
                out = response.getWriter();
                out.append(json);
                return false;
            }
            // 校验 token
            Optional<DecodedJWT> optional = jwtUtil.getTokenInfo(token);
            if (optional.isPresent()) {
                DecodedJWT decodedJWT = optional.get();
                String account = decodedJWT.getClaim(CommonField.ACCOUNT).asString();
                // 将 token 中的 account 放到 request 里面，转发到业务
                request.setAttribute(CommonField.ACCOUNT, account);
            }
        } catch (Exception e) {
            log.error("TokenVerifyInterceptor preHandle error: {}", e.getMessage(), e);
            // 验证失败，返回 401 状态码
            ResponseEntity<?> responseEntity = new ResponseEntity<>(ServerStatusCode.UNKNOWN);
            String json = JSON.toJSONString(responseEntity);
            out = response.getWriter();
            out.append(json);
            return false;
        }
        return true;
    }
}
