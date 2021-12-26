package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.UserInfoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

/**
 * 用户个人资料 PO
 *
 * @author yeeq
 * @date 22//8
 */
@Data
@Slf4j
@TableName("y_c_user_info")
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends BasePO {

    /**
     * id
     */
    @TableField("id")
    private String id;

    /**
     * 账号
     */
    @TableId("account")
    private String account;

    /**
     * 个人介绍
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 公司
     */
    @TableField("company")
    private String company;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建者
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 更新者
     */
    @TableField("update_user")
    private String updateUser;

    @Override
    public Optional<BaseVO> toVO() {
        UserInfoVO userInfoVO;
        try {
            userInfoVO = new UserInfoVO();
            userInfoVO.setIntroduction(introduction);
            userInfoVO.setSex(sex);
            userInfoVO.setCompany(company);
            userInfoVO.setPosition(position);
        } catch (Exception e) {
            log.error("UserInfo toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userInfoVO);
    }
}
