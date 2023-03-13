package com.styeeqan.community.listener;

import com.alibaba.fastjson.JSON;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.RsaUtil;
import com.styeeqan.community.common.util.ThreadUtil;
import com.styeeqan.community.pojo.dto.KeyPairDto;
import com.styeeqan.community.task.UserContributeDayRankTask;
import com.styeeqan.community.task.UserDynamicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
        // 用户动态
        threadUtil.execute(() -> {
            while (true) {
                UserDynamicTask userDynamicTask = null;
                try {
                    Optional<String> optional
                            = redisUtil.popListLeftValue(RedisKey.USER_DYNAMIC_TASK_LIST, null, Duration.ofSeconds(10L));
                    if (optional.isPresent()) {
                        userDynamicTask = JSON.parseObject(optional.get(), UserDynamicTask.class);
                        log.info("开始保存用户动态:{}", JSON.toJSONString(userDynamicTask));
                        userDynamicTask.execute();
                    }
                } catch (Exception e) {
                    log.info("保存用户动态失败:{}", JSON.toJSONString(userDynamicTask));
                }
            }
        });
        // 用户贡献日榜
        threadUtil.execute(() -> {
            while (true) {
                UserContributeDayRankTask dayRankTask = null;
                try {
                    Optional<String> optional
                            = redisUtil.popListLeftValue(RedisKey.USER_CONTRIBUTE_DAY_TASK_LIST, null, Duration.ofSeconds(10L));
                    if (optional.isPresent()) {
                        dayRankTask = JSON.parseObject(optional.get(), UserContributeDayRankTask.class);
                        log.info("开始更新用户贡献日榜:{}", JSON.toJSONString(dayRankTask));
                        dayRankTask.execute();
                    }
                } catch (Exception e) {
                    log.info("更新用户贡献日榜失败:{}", JSON.toJSONString(dayRankTask));
                }
            }
        });
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
