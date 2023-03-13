package com.styeeqan.community.task;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.common.util.DateUtil;
import com.styeeqan.community.common.util.SpringBeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserContributeDayRankTask implements Serializable {

    private String account;

    private String username;

    private String headPortrait;

    private String homepageId;

    @JsonIgnore
    private final RedisUtil redisUtil = SpringBeanUtil.getBean(RedisUtil.class);

    @JsonIgnore
    private final DateUtil dateUtil = SpringBeanUtil.getBean(DateUtil.class);

    @SneakyThrows
    public void execute() {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = UserContributeDayRankTask.class.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            Object value = field.get(this);
            if (String.class == value.getClass()) {
                jsonObject.put(key, String.valueOf(value));
            }
        }
        String json = jsonObject.toJSONString();
        redisUtil.setZSetValue(RedisKey.USER_CONTRIBUTE_DAY,
                dateUtil.getDateStr(new Date(), DateUtil.parse_date_pattern_1), json, 10);
    }
}
