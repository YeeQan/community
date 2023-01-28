package com.styeeqan.community.web.controller;

import com.styeeqan.community.common.constant.ServerStatusCode;
import com.styeeqan.community.common.http.response.ResponseEntity;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/8/2
 */
@Slf4j
@RestController
@Api(tags = "通用功能Controller")
public class CommonCon {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("publicKey")
    @ApiOperation(value = "获取公钥")
    public ResponseEntity<?> publish() {
        Optional<String> publicKeyOp = redisUtil.getValue(RedisKey.PUBLIC_KEY, null);
        if (publicKeyOp.isPresent()) {
            return new ResponseEntity<>(publicKeyOp.get());
        } else {
            return new ResponseEntity<>(ServerStatusCode.GET_PUBLIC_KEY_ERROR);
        }
    }
}