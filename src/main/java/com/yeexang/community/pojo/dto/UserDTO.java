package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author yeeq
 * @date 2021/7/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDTO extends BaseDTO {

    private String account;
    private String username;
    private String password;
    private String levelTitle;

    public UserDTO(String account, String username, String password) {
        this.account = account;
        this.username = username;
        this.password = password;
    }

    @Override
    public BasePO toPO() {
        User user = new User();
        user.setAccount(account);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
