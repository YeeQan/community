package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户个人主页 VO
 *
 * @author yeeq
 * @date 2021/12/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserHomepageVO extends BaseVO {

    private String username;
    private String headPortrait;
    private Boolean self;
    private UserInfoVO userInfoVO;
    private List<UserDynamicVO> userDynamicVOList;
}
