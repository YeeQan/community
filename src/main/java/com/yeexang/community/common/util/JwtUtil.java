package com.yeexang.community.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Component
public class JwtUtil {

    @Value("${jwt.token-secret}")
    private String tokenSecret;

    /**
     * 生成 token
     */
    public String getToken(Map<String, String> map) {
        // TOKEN 有效期七天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        // 创建 payload
        map.forEach(builder::withClaim);
        // 签名，并指定密钥
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(tokenSecret));
    }

    /**
     * 验证 token 的合法性
     */
    public void verifyJwt(String token) {
        JWT.require(Algorithm.HMAC256(tokenSecret)).build().verify(token);
    }

    /**
     * 获取 token 的信息
     */
    public DecodedJWT getTokenInfo(String token) {
        return JWT.require(Algorithm.HMAC256(tokenSecret)).build().verify(token);
    }
}
