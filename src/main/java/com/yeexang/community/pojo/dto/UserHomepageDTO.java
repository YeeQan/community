package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.UserHomepage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 用户个人主页 DTO
 *
 * @author yeeq
 * @date 2021/12/19
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class UserHomepageDTO extends BaseDTO {

    private String homepageId;

    @Override
    public Optional<BasePO> toPO() {
        UserHomepage userHomepage;
        try {
            userHomepage = new UserHomepage();
            userHomepage.setHomepageId(homepageId);
        } catch (Exception e) {
            log.error("UserHomepageDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(userHomepage);
    }
}
