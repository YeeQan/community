package com.styeeqan.community;

import com.alibaba.fastjson.JSON;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.ThreadUtil;
import com.styeeqan.community.task.UserDynamicTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
public class ApplicationListener implements org.springframework.context.ApplicationListener {

    @Autowired
    private ThreadUtil threadUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 用户动态
        threadUtil.execute(() -> {
            while (true) {
                Optional<String> optional
                        = redisUtil.popListLeftValue(RedisKey.USER_DYNAMIC_TASK_LIST, null, Duration.ofSeconds(10L));
                if (optional.isPresent()) {
                    UserDynamicTask userDynamicTask = JSON.parseObject(optional.get(), UserDynamicTask.class);
                    log.info("开始保存用户动态:{}", JSON.toJSONString(userDynamicTask));
                    userDynamicTask.execute();
                }
            }
        });
    }
}
