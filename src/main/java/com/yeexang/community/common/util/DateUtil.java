package com.yeexang.community.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author yeeq
 * @date 2021/10/11
 */
@Slf4j
@Component
public class DateUtil {

    private final long ONE_MINUTE = 60000L;
    private final long ONE_HOUR = 3600000L;
    private final long ONE_DAY = 86400000L;
    private final long ONE_WEEK = 604800000L;

    private final String ONE_SECOND_AGO = "秒前";
    private final String ONE_MINUTE_AGO = "分钟前";
    private final String ONE_HOUR_AGO = "小时前";
    private final String ONE_DAY_AGO = "天前";
    private final String ONE_MONTH_AGO = "月前";
    private final String ONE_YEAR_AGO = "年前";

    /**
     * 获取当前相对时间
     *
     * @param date date
     * @return relativeDate
     */
    public String relativeDateFormat(Date date) {
        long delta = System.currentTimeMillis() - date.getTime();
        if (ONE_MINUTE > delta) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private long toSeconds(long date) {
        return date / 1000L;
    }

    private long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private long toDays(long date) {
        return toHours(date) / 24L;
    }

    private long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
