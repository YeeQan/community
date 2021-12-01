package com.yeexang.community.common.constant;

/**
 * 常量工具类
 *
 * @author yeeq
 * @date 2021/7/23
 */
public class CommonField {

    /**
     * token
     */
    public static final String TOKEN = "token";

    /**
     * redis 默认值
     */
    public static final String REDIS_DEFAULT_VALUE = "#null";

    /**
     * topic_visit_count 常量
     */
    public static final String TOPIC_VISIT_COUNT_CONSTANT = "topic_visit_count_constant";

    /**
     * topic 展示全部
     */
    public static final String TOPIC_ALL = "#all";

    /**
     * 用户账户
     */
    public static final String ACCOUNT = "account";

    /**
     * 评论类型 —— 一级评论
     */
    public static final String FIRST_LEVEL_COMMENT = "1";

    /**
     * 评论类型 —— 二级评论
     */
    public static final String SECOND_LEVEL_COMMENT = "2";

    /**
     * 通知类型 —— 回帖
     */
    public static final String NOTIFICATION_TYPE_TOPIC = "1";

    /**
     * 通知类型 —— 回复评论
     */
    public static final String NOTIFICATION_TYPE_COMMENT = "2";

    /**
     * 通知类型 —— 点赞帖子
     */
    public static final String NOTIFICATION_TYPE_LIKE_TOPIC = "3";

    /**
     * 通知类型 —— 点赞评论
     */
    public static final String NOTIFICATION_TYPE_LIKE_COMMENT = "4";

    /**
     * 账号必须由字母、数字、下划线组成，不能超过12位
     */
    public static final String ACCOUNT_FORMAT_REGULAR = "[a-zA-Z0-9_]{1,12}";

    /**
     * 昵称格式必须由字母、数字、下划线组成
     */
    public static final String USERNAME_FORMAT_REGULAR = "[\\u4E00-\\u9FA5A-Za-z0-9_]+$";

    /**
     * 密码格式必须由字母、数字、下划线组成，不能超过16位
     */
    public static final String PASSWORD_FORMAT_REGULAR = "[a-zA-Z0-9]{1,16}";
}
