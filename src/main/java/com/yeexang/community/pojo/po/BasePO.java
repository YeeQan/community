package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.vo.BaseVO;

import java.util.Optional;

/**
 * 基础 PO
 *
 * @author yeeq
 * @date 2021/7/23
 */
public abstract class BasePO {

    /**
     * PO 转换为 VO
     * @return BaseDTO
     */
    public abstract Optional<BaseVO> toVO();
}
