package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户个人资料 VO
 *
 * @author yeeq
 * @date 2021/12/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseVO {

    private String introduction;
    private String sex;
    private String company;
    private String position;
}
