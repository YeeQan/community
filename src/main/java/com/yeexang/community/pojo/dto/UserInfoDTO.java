package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 用户个人资料 DTO
 *
 * @author yeeq
 * @date 2021/12/18
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class UserInfoDTO extends BaseDTO {

    private String account;
    private String introduction;
    private String sex;
    private String company;
    private String position;

    @Override
    public Optional<BasePO> toPO() {
        UserInfo userInfo;
        try {
            userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setIntroduction(introduction);
            userInfo.setSex(sex);
            userInfo.setCompany(company);
            userInfo.setPosition(position);
        } catch (Exception e) {
            log.error("UserInfoDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userInfo);
    }
}
