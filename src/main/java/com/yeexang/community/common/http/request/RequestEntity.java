package com.yeexang.community.common.http.request;

import com.yeexang.community.common.filter.Filter;
import lombok.Data;

import java.util.List;

/**
 * 通用请求类
 *
 * @author yeeq
 * @date 2021/7/23
 */
@Data
public class RequestEntity<T> {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 单页数量
     */
    private Integer pageSize;

    /**
     * 筛选器
     */
    private Filter filter;

    /**
     * 请求参数
     */
    private List<T> data;
}
