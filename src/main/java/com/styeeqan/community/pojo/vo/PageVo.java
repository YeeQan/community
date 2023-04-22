package com.styeeqan.community.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 所有导航页号
     */
    private int[] navigatepageNums;

    /**
     * 数据
     */
    private List<T> data;
}
