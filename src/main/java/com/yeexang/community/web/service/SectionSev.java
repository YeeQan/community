package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.po.Section;

import java.util.List;

/**
 * 专栏管理 Service
 *
 * @author yeeq
 * @date 2021/7/26
 */
public interface SectionSev {

    /**
     * 获取专栏
     * @param sectionDTO sectionDTO
     * @return List<Section>
     */
    List<Section> getSection(SectionDTO sectionDTO);
}
