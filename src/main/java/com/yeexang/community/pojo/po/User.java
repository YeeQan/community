package com.yeexang.community.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.UserVO;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

/**
 * 用户 PO
 *
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@Slf4j
@TableName("y_c_user")
@EqualsAndHashCode(callSuper = false)
public class User extends BasePO {

    /**
     * 主键
     */
    @TableField("id")
    private String id;

    /**
     * 账号，唯一，用于登录
     */
    @TableId("account")
    private String account;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户头像 AliyunOSS url
     */
    @TableField("head_portrait")
    private String headPortrait;

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

    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Boolean delFlag;

    @Override
    public Optional<BaseVO> toVO() {
        UserVO userVO;
        try {
            userVO = new UserVO();
            userVO.setUsername(username);
            userVO.setHeadPortrait(headPortrait);
        } catch (Exception e) {
            log.error("User toVO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userVO);
    }
}
