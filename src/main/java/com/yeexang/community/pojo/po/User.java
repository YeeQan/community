package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author yeeq
 * @date 2021/7/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends BasePO {

    /**
     * 主键
     */
    private String id;

    /**
     * 账号，唯一，用于登录
     */
    private String account;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 经验值
     */
    private Integer exp;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 删除标识
     */
    private Boolean delFlag;

    @Override
    public BaseDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(account);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        return userDTO;
    }

    public User(String account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }
}
