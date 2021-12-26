package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户动态 VO
 *
 * @author yeeq
 * @date 2021/12/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDynamicVO extends BaseVO {

    private String createUsername;
    private String headPortrait;
    private String targetId;
    private String targetName;
    private String dynamicType;
    private String dynamicContent;
    private String relativeDate;
}
