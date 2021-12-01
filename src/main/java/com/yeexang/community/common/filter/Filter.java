package com.yeexang.community.common.filter;

import lombok.Getter;

/**
 * @author yeeq
 * @date 2021/11/30
 */
@Getter
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
     * 筛选加精的帖子
     */
    private boolean topicEssential;
}
