package com.yeexang.community.pojo.po;

import com.yeexang.community.pojo.dto.BaseDTO;
import com.yeexang.community.pojo.dto.UserDTO;
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
     * 用户头像 AliyunOSS url
     */
    private String headPortrait;

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
    public Optional<BaseDTO> toDTO() {
        UserDTO userDTO;
        try {
            userDTO = new UserDTO();
            userDTO.setAccount(account);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setHeadPortrait(headPortrait);
        } catch (Exception e) {
            log.error("User toDTO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userDTO);
    }

    public User(String account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }
}
