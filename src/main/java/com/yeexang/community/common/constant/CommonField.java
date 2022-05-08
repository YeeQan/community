package com.yeexang.community.common.constant;

import java.util.List;
import java.util.Map;

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

    /**
     * 用户动态类型 —— 发布帖子
     */
    public static final String USER_PUBLIC_TOPIC_DYNAMIC_TYPE = "1";

    /**
     * 用户动态类型 —— 发表评论
     */
    public static final String USER_PUBLIC_COMMENT_DYNAMIC_TYPE = "2";

    /**
     * 用户动态类型 —— 点赞帖子
     */
    public static final String USER_LIKE_TOPIC_DYNAMIC_TYPE = "3";

    /**
     * 用户动态类型 —— 用户注册
     */
    public static final String USER_REGISTER_DYNAMIC_TYPE = "4";
}
