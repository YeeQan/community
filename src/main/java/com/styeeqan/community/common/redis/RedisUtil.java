package com.styeeqan.community.common.redis;

import com.styeeqan.community.common.constant.CommonField;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
     */
    public Optional<String> getValue(RedisKey redisKey, String id) {
        return Optional.ofNullable(template.opsForValue().get(redisKey.getKey(id)));
    }

    /**
     * 根据 key 删除
     */
    public void delete(RedisKey redisKey, String id) {
        template.delete(redisKey.getKey(id));
    }

    /**
     * 设置 hash-key 对应 value 自增 delta
     */
    public void setHashIncr(RedisKey redisKey, String key, long delta) {
        template.opsForHash().increment(redisKey.getKey(null), key, delta);
    }

    /**
     * 获取HashMap
     */
    public Map<Object, Object> getHashMap(RedisKey redisKey) {
        return template.opsForHash().entries(redisKey.getKey(null));
    }

    /**
     * 设置 Zset-Value
     */
    public void setZSetValue(RedisKey redisKey, String id, String value, Integer score) {
        template.opsForZSet().incrementScore(redisKey.getKey(id), value, score);
    }

    /**
     * 获取 Zset 排名内的值
     */
    public Set<String> reverseRangeZSet(RedisKey redisKey, String id, int start, int end) {
        return template.opsForZSet().reverseRange(redisKey.getKey(id), start, end);
    }

    /**
     * 获取 Zset 指定 socre
     */
    public Optional<Double> scoreZSet(RedisKey redisKey, String id, String value) {
        return Optional.ofNullable(template.opsForZSet().score(redisKey.getKey(id), value));

    }
}