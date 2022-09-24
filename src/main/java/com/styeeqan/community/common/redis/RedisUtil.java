package com.styeeqan.community.common.redis;

import com.alibaba.fastjson.JSON;
import com.styeeqan.community.common.constant.CommonField;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
     * 获取 Object-value
     *
     * @param clazz    对象类型
     * @param redisKey redisKey
     * @param id       id
     * @return Object
     */
    public Optional<?> getObjectValue(Class<?> clazz, RedisKey redisKey, String id) {
        Object parseObject = null;
        Optional<String> optional = getValue(redisKey, id);
        if (optional.isPresent()) {
            String value = optional.get();
            parseObject = JSON.parseObject(value, clazz);
        }
        return Optional.ofNullable(parseObject);
    }

    /**
     * 设置 Object-value
     *
     * @param redisKey redisKey
     * @param id       id
     * @param object   object
     */
    public void setObjectValue(RedisKey redisKey, String id, Object object) {
        if (redisKey.getTimeout() == null) {
            template.opsForValue().set(redisKey.getKey(id), JSON.toJSONString(object));
        } else {
            template.opsForValue().set(redisKey.getKey(id), JSON.toJSONString(object), redisKey.getTimeout());
        }
    }
}