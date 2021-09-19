package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;

import java.util.Optional;

/**
 * 基础 DTO
 *
 * @author yeeq
 * @date 2021/7/23
 */
public abstract class BaseDTO {

    /**
     * DTO 转换为 PO
     * @return BasePO
     */
    public abstract Optional<BasePO> toPO();
}
