package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yeeq
 * @date 2021/7/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SectionDTO extends BaseDTO {

    private String sectionId;
    private String sectionName;

    @Override
    public BasePO toPO() {
        Section section = new Section();
        section.setSectionId(sectionId);
        section.setSectionName(sectionName);
        return section;
    }
}
