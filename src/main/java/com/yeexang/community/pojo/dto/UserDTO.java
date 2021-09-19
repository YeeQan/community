package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 用户 DTO
 *
 * @author yeeq
 * @date 2021/7/23
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDTO extends BaseDTO {

    private String account;
    private String username;
    private String password;

    public UserDTO(String account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<BasePO> toPO() {
        User user;
        try {
            user = new User();
            user.setAccount(account);
            user.setUsername(username);
            user.setPassword(password);
        } catch (Exception e) {
            log.error("UserDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
