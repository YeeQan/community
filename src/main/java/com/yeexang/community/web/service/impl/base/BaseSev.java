package com.yeexang.community.web.service.impl.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.constant.CommonField;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;

/**
 * BaseDao
 *
 * @author yeeq
 * @date 2021/11/20
 */
public abstract class BaseSev<Entity, ID> {

    @Autowired
    protected RedisUtil redisUtil;

    /**
     * 获取对应 Entity 在 Redis 中的 Key
     *
     * @return RedisKey
     */
    protected abstract RedisKey getRedisKey();

    /**
     * 获取对应 Entity 的操作对象
     *
     * @return BaseMapper<Entity>
     */
    protected abstract BaseMapper<Entity> getBaseMapper();

    /**
     * 获取对应 Entity 的 Class 类型
     *
     * @return Class<Entity>
     */
    protected abstract Class<Entity> getEntityClass();

    /**
     * 根据 ID 获取 Entity
     * @param id id
     * @return Entity
     */
    protected Entity selectById(ID id) {
        Entity entity = null;
        RedisKey redisKey = this.getRedisKey();
        Optional<?> rovOP = redisUtil.getObjectValue(getEntityClass(), redisKey, id.toString());
        // 说明 Redis 中没有值
        if (rovOP.isEmpty()) {
            // 防止并发操作
            synchronized (this) {
                // 二次获取
                rovOP = redisUtil.getObjectValue(getEntityClass(), redisKey, id.toString());
                // 如果 Redis 中还是没有值，再从数据库获取
                if (rovOP.isEmpty()) {
                    entity = this.getBaseMapper().selectById((Serializable) id);
                    if (entity != null) {
                        this.save(entity, id);
                    } else {
                        // 设置 Redis 缓存默认值，防止缓存击穿
                        this.setRedisDefaultValue(id);
                    }
                }
            }
        }
        if (rovOP.isPresent()) {
            // 取出来的是默认值，返回空，表示不存在，否则返回值
            Object valueObj = rovOP.get();
            if (!CommonField.REDIS_DEFAULT_VALUE.equals(valueObj.toString())) {
                entity = (Entity) valueObj;
            }
        }
        return entity;
    }

    /**
     * 保存数据
     *
     * @param entity entity
     * @param id id
     */
    protected void save(Entity entity, ID id) {
        // 如果存在数据就更新，否则插入
        Entity entityDB = this.getBaseMapper().selectById((Serializable) id);
        if (entityDB == null) {
            this.getBaseMapper().insert(entity);
        } else {
            this.getBaseMapper().updateById(entity);
        }
        // 保存数据到 Redis 中
        redisUtil.setObjectValue(getRedisKey(), id.toString(), entityDB);
    }

    /**
     * 设置 Redis 默认值
     * @param id id
     */
    private void setRedisDefaultValue(ID id) {
        redisUtil.setValue(getRedisKey(), id.toString(), CommonField.REDIS_DEFAULT_VALUE);
    }
}