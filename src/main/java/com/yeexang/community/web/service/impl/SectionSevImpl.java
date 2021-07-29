package com.yeexang.community.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.common.redis.RedisUtil;
import com.yeexang.community.dao.SectionDao;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Section;
import com.yeexang.community.web.service.SectionSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/26
 */
@Slf4j
@Service
public class SectionSevImpl implements SectionSev {

    @Autowired
    private SectionDao sectionDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Section> getSection(SectionDTO sectionDTO) {
        Section section = (Section) sectionDTO.toPO();
        List<Section> sectionList = new ArrayList<>();
        try {
            Section sectionCache = (Section) redisUtil.getObjectValue(Section.class, RedisKey.SECTION, section.getSectionId());
            if (sectionCache == null) {
                Section sectionParam = new Section();
                section.setSectionId(section.getSectionId());
                List<Section> select = sectionDao.select(sectionParam);
                sectionList.addAll(select);
                sectionList.forEach(s ->
                        redisUtil.setValue(RedisKey.SECTION, s.getSectionId(), JSON.toJSONString(s)));
            }
        } catch (Exception e) {
            log.error("SectionSev getSection errorMsg: {}", e.getMessage());
        }
        return sectionList;
    }
}
