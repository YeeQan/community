package com.yeexang.community.pojo.dto;

import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Section;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 专栏 DTO
 *
 * @author yeeq
 * @date 2021/7/26
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SectionDTO extends BaseDTO {

    private String sectionId;
    private String sectionName;

    @Override
    public Optional<BasePO> toPO() {
        Section section;
        try {
            section = new Section();
            section.setSectionId(sectionId);
            section.setSectionName(sectionName);
        } catch (Exception e) {
            log.error("SectionDTO toPO errorMsg: {}", e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(section);
    }
}
