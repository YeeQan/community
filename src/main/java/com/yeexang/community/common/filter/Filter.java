package com.yeexang.community.common.filter;

import lombok.Data;

/**
 * @author yeeq
 * @date 2021/11/30
 */
@Data
public class Filter {

    /**
     * 创建时间倒序
     */
    private boolean createTimeDesc;

    /**
     * 创建时间正序
     */
    private boolean createTimeAsc;

    /**
     * 最后一次评论时间正序
     */
    private boolean lastCommentTimeAsc;

    /**
     * 最后一次评论时间倒序
     */
    private boolean lastCommentTimeDesc;

    /**
     * 筛选加精的帖子
     */
    private boolean topicEssential;
}
