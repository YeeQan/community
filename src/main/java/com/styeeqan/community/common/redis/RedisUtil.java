package com.styeeqan.community.common.redis;

import com.styeeqan.community.common.constant.CommonField;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Optional;

/**
 * Redis 工具类
 *
 * @author yeeq
 * @date 2021/7/26
 */
@Component
public class RedisUtil {

    @Resource
    private StringRedisTemplate template;

    /**
     * 设置 value
     * @param redisKey redisKey
     * @param id id
     * @param value value
     */
    public void setValue(RedisKey redisKey, String id, String value) {
        if (redisKey.getTimeout() == null) {
            template.opsForValue().set(redisKey.getKey(id), value);
        } else {
            template.opsForValue().set(redisKey.getKey(id), value, redisKey.getTimeout());
        }
    }

    /**
     * 获取 value
     * @param redisKey redisKey
     * @param id id
     * @return String
     */
    public Optional<String> getValue(RedisKey redisKey, String id) {
        String value = template.opsForValue().get(redisKey.getKey(id));
        if (CommonField.REDIS_DEFAULT_VALUE.equals(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(value);
    }

    /**
     * 根据 key 删除
     * @param redisKey redisKey
     * @param id id
     */
    public void delete(RedisKey redisKey, String id) {
        template.delete(redisKey.getKey(id));
    }

    /**
     * 把 value 加到 list 尾部
     * @param redisKey redisKey
     * @param id id
     * @param value value
     */
    public void pushListRightValue(RedisKey redisKey, String id, String value) {
        template.opsForList().rightPush(redisKey.getKey(id), value);
    }

    /**
     * 从 list 头部获取 value
     * @param redisKey redisKey
     * @param id id
     */
    public Optional<String> popListLeftValue(RedisKey redisKey, String id, Duration timeout) {
        String value = template.opsForList().leftPop(redisKey.getKey(id), timeout);
        if (CommonField.REDIS_DEFAULT_VALUE.equals(value)) {
            return Optional.empty();
        }
        return Optional.ofNullable(value);
    }
}