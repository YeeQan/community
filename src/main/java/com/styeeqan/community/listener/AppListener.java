package com.styeeqan.community.listener;

import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.RsaUtil;
import com.styeeqan.community.common.util.ThreadUtil;
import com.styeeqan.community.pojo.dto.KeyPairDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AppListener implements ApplicationListener {

    @Autowired
    private ThreadUtil threadUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RsaUtil rsaUtil;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 生成RSA非对称加密密钥对
        Optional<KeyPairDto> keyPairDtoOp = rsaUtil.generateKeyPair();
        keyPairDtoOp.ifPresent(keyPairDto -> {
            // 保存公钥
            redisUtil.setValue(RedisKey.PUBLIC_KEY, null, keyPairDto.getPublicKey());
            // 保存公钥对应的私钥
            redisUtil.setValue(RedisKey.PRIVATE_KEY, keyPairDto.getPublicKey(), keyPairDto.getPrivateKey());
        });
    }
}
