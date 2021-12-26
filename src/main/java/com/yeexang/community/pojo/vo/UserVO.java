package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户 VO
 *
 * @author yeeq
 * @date 2021/11/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {

    private String username;
    private String headPortrait;
    private String homepageId;
}
