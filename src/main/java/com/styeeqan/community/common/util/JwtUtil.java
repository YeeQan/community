package com.styeeqan.community.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

/**
 * JWT 工具类
 *
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
    public Optional<String> getToken(Map<String, String> map) {
        // TOKEN 有效期七天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        // 创建 payload
        map.forEach(builder::withClaim);
        // 签名，并指定密钥
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(tokenSecret));
        return Optional.ofNullable(token);
    }

    /**
     * 获取 token 的信息
     */
    public Optional<DecodedJWT> getTokenInfo(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC256(tokenSecret)).build().verify(token);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(decodedJWT);
    }
}
