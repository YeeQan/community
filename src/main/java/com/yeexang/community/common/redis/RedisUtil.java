package com.yeexang.community.common.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yeeq
 * @date 2021/7/26
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate template;

    public void setValue(RedisKey redisKey, String id, String value) {
        if (redisKey.getTimeout() == null) {
            template.opsForValue().set(redisKey.getKey(id), value);
        } else {
            template.opsForValue().set(redisKey.getKey(id), value, redisKey.getTimeout());
        }
    }

    public String getValue(RedisKey redisKey, String id) {
        return template.opsForValue().get(redisKey.getKey(id));
    }

    public Object getObjectValue(Class<? extends Object> clazz, RedisKey redisKey, String id) {
        String value = getValue(redisKey, id);
        return JSON.parseObject(value, clazz);
    }
}
