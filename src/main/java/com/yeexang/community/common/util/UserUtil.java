package com.yeexang.community.common.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.constant.ServerStatusCode;
import com.yeexang.community.common.http.response.ResponseEntity;
import com.yeexang.community.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 用户工具类
 *
 * @author yeeq
 * @date 2021/12/9
 */
@Slf4j
@Component
public class UserUtil {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取已登录用户的 account
     * @return Optional<String>
     */
    public Optional<String> getLoginUserAccount(HttpServletRequest request) {
        String account = null;
        try {
            Cookie[] cookies = request.getCookies();
            String token = null;
            if (cookies != null && cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (CommonField.TOKEN.equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }
            if (!StringUtils.isEmpty(token)) {
                Optional<DecodedJWT> optional = jwtUtil.getTokenInfo(token);
                if (optional.isPresent()) {
                    DecodedJWT decodedJWT = optional.get();
                    account = decodedJWT.getClaim(CommonField.ACCOUNT).asString();
                }
            }
        } catch (Exception e) {
            log.error("UserUtil getLoginUserAccount errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(account);
    }
}
