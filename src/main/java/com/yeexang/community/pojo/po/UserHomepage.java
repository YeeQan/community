package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.UserHomepageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

/**
 * 用户个人主页 PO
 *
 * @author yeeq
 * @date 2021/12/19
 */
@Data
@Slf4j
@TableName("y_c_user_homepage")
@EqualsAndHashCode(callSuper = false)
public class UserHomepage extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 主键
     */
    @TableId("homepage_id")
    private String homepageId;

    /**
     * 账号，唯一，用于登录
     */
    @TableField("account")
    private String account;

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
        UserHomepageVO userHomepageVO;
        try {
            userHomepageVO = new UserHomepageVO();
        } catch (Exception e) {
            log.error("UserHomepage toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userHomepageVO);
    }
}
