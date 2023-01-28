package com.styeeqan.community.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.styeeqan.community.common.http.request.RequestWrapper;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.IpUtil;
import com.styeeqan.community.common.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 解密拦截器
 *
 * @author yeeq
 * @date 2021/12/16
 */
@Slf4j
@Component
public class DecryptInterceptor implements HandlerInterceptor {

    @Autowired
    private RsaUtil rsaUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IpUtil ipUtil;

    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String uri = request.getRequestURI();

        // 解析body为json
        RequestWrapper requestWrapper = (RequestWrapper) request;

        String body = requestWrapper.getBody();
        if (StringUtils.isEmpty(body)) {
            return true;
        }

        JSONObject jsonData = JSON.parseObject(body);

        // 登录/注册操作,对密码解密
        if (uri.contains("user/login") ||
                uri.contains("user/register")) {
            String password = jsonData.getString("password");
            String publicKey = jsonData.getString("publicKey");
            // 对密码解密
            if (!StringUtils.isEmpty(publicKey)) {
                Optional<String> privateKeyOp = redisUtil.getValue(RedisKey.PRIVATE_KEY, publicKey);
                if (privateKeyOp.isPresent()) {
                    Optional<String> decryptOp = rsaUtil.decrypt(password, privateKeyOp.get());
                    if (decryptOp.isPresent()) {
                        password = decryptOp.get();
                    }
                }
            }
            jsonData.put("password", password);
        }

        // 保存解密数据
        ((RequestWrapper) request).setBody(jsonData.toJSONString());

        return true;
    }
}
