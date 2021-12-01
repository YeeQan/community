package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageVO<T> extends BaseVO {

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
    private List<T> list;
}
