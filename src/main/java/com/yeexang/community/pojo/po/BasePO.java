package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;

import java.util.Optional;

/**
 * 基础 PO
 *
 * @author yeeq
 * @date 2021/7/23
 */
public abstract class BasePO {

    /**
     * PO 转换为 DTO
     * @return BaseDTO
     */
    public abstract Optional<BaseDTO> toDTO();
}
