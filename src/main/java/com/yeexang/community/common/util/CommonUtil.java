package com.yeexang.community.common.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/8/9
 */
@Component
public class CommonUtil {

    /**
     * 生成 32 位随机码
     * @return String
     */
    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成 20 位的随机码（14位年月日时分秒 + 6位随机数）
     * @return String
     */
    public String randomCode() {
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = new Random().nextInt(1000000);
        return dateStr + random;
    }
}
