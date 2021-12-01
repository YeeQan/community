package com.yeexang.community.web.service;

import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.po.Section;
import com.yeexang.community.pojo.vo.SectionVO;

import java.util.List;
import java.util.Optional;

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
     * @return List<SectionVO>
     */
    List<SectionVO> getSectionList(SectionDTO sectionDTO);
}
