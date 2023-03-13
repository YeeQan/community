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

    /**
     * 设置 hash-key 对应 value 自增 delta
     * @param redisKey redisKey
     * @param key hashkey
     * @param delta delta
     */
    public void setHashIncr(RedisKey redisKey, String key, long delta) {
        template.opsForHash().increment(redisKey.getKey(null), key, delta);
    }

    /**
     * 获取HashMap
     * @param redisKey redisKey
     * @return Map<Object, Object>
     */
    public Map<Object, Object> getHashMap(RedisKey redisKey) {
        return template.opsForHash().entries(redisKey.getKey(null));
    }

    /**
     * 设置 Zset-Value
     * @param redisKey redisKey
     * @param id id
     * @param value value
     * @param score score
     */
    public void setZSetValue(RedisKey redisKey, String id, String value, Integer score) {
        template.opsForZSet().incrementScore(redisKey.getKey(id), value, score);
    }

    /**
     * 获取 Zset 排名内的值
     * @param redisKey redisKey
     * @param id id
     * @param start start
     * @param end end
     * @return Set<String>
     */
    public Set<String> reverseRangeZSet(RedisKey redisKey, String id, int start, int end) {
        return template.opsForZSet().reverseRange(redisKey.getKey(id), start, end);
    }

    /**
     * 获取 Zset 指定 socre
     * @param redisKey
     * @param id
     * @param value
     * @return
     */
    public Double scoreZSet(RedisKey redisKey, String id, String value) {
        return template.opsForZSet().score(redisKey.getKey(id), value);
    }
}