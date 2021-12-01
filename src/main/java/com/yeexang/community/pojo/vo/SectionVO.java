package com.yeexang.community.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yeeq
 * @date 2021/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SectionVO extends BaseVO {

    private String sectionId;
    private String sectionName;
}
