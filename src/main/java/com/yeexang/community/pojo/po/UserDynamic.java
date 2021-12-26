package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.UserDynamicVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

/**
 * @author yeeq
 * @date 2021/12/11
 */
@Data
@Slf4j
@TableName("y_c_user_dynamic")
@EqualsAndHashCode(callSuper = false)
public class UserDynamic extends BasePO {

    /**
     * 唯一id
     */
    @TableField("id")
    private String id;

    /**
     * 动态id
     */
    @TableId("dynamic_id")
    private String dynamicId;

    /**
     * 用户id
     */
    @TableField("account")
    private String account;

    /**
     * 目标id
     */
    @TableField("target_id")
    private String targetId;

    /**
     * 动态类型
     */
    @TableField("dynamic_type")
    private String dynamicType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    @Override
    public Optional<BaseVO> toVO() {
        UserDynamicVO userDynamicVO;
        try {
            userDynamicVO = new UserDynamicVO();
            userDynamicVO.setTargetId(targetId);
            userDynamicVO.setDynamicType(dynamicType);
        } catch (Exception e) {
            log.error("UserDynamic toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userDynamicVO);
    }
}
