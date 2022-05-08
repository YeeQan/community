package com.yeexang.community.common.constant;

import com.yeexang.community.common.http.response.KeyValueResult;

import java.util.List;
import java.util.Map;

/**
 * 通知类型
 *
 * @author yeeq
 * @date 2022/5/7
 */
public class NotificationField {

    /**
     * 回复通知标签
     */
    public static final String COMMENT_LABEL = "comment";

    /**
     * 点赞帖子标签
     */
    public static final String TOPIC_LIKE_LABEL = "topicLike";

    /**
     * 回复帖子值
     */
    public static final String TOPIC_VALUE = "1";

    /**
     * 回复评论值
     */
    public static final String COMMENT_VALUE = "2";

    /**
     * 点赞帖子值
     */
    public static final String TOPIC_LIKE_VALUE = "3";

    public static final List<String> LABEL_LIST = List.of(
            COMMENT_LABEL, TOPIC_LIKE_LABEL
    );

    public static final Map<String, List<String>> LABEL_VALUE_MAP = Map.of(
            COMMENT_LABEL, List.of(TOPIC_VALUE, COMMENT_VALUE),
            TOPIC_LIKE_LABEL, List.of(TOPIC_LIKE_VALUE)
    );

    /**
     * 获取标签名
     * @return LabelName
     */
    public static String getLabelName(String label) {
        switch (label) {
            case COMMENT_LABEL:
                return "评论";
            case TOPIC_LIKE_LABEL:
                return "点赞";
            default:
                return null;
        }
    }
}
