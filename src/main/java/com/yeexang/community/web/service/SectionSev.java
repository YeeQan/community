package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.po.Section;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/26
 */
public interface SectionSev {

    List<Section> getSection(SectionDTO sectionDTO);
}
