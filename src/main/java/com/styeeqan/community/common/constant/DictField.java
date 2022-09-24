package com.styeeqan.community.common.constant;

/**
 * 字典常量工具类
 *
 * @author yeeq
 * @date 2021/9/28
 */
public class DictField {

    public enum Type {
        // 用户默认头像
        user_default_head_portrait,
        // 用户性别
        user_sex;
    }

    public enum Dict {

        user_sex_unknown("user_sex_unknown", Type.user_sex.name(), "2", "用户性别未知"),
        user_sex_male("user_sex_male", Type.user_sex.name(), "2", "用户性别男"),
        user_sex_female("user_sex_female", Type.user_sex.name(), "2", "用户性别女"),
        ;

        public final String label;
        public final String type;
        public final String defaultValue;
        public final String desc;

        Dict(String label, String type, String defaultValue, String desc) {
            this.label = label;
            this.type = type;
            this.defaultValue = defaultValue;
            this.desc = desc;
        }
    }
}
