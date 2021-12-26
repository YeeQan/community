package com.yeexang.community.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeexang.community.common.redis.RedisKey;
import com.yeexang.community.dao.SectionDao;
import com.yeexang.community.pojo.dto.SectionDTO;
import com.yeexang.community.pojo.po.BasePO;
import com.yeexang.community.pojo.po.Section;
import com.yeexang.community.pojo.vo.BaseVO;
import com.yeexang.community.pojo.vo.SectionVO;
import com.yeexang.community.web.service.impl.base.BaseSev;
import com.yeexang.community.web.service.SectionSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yeeq
 * @date 2021/7/26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SectionSevImpl extends BaseSev<Section, String> implements SectionSev {

    @Autowired
    private SectionDao sectionDao;

    @Override
    protected RedisKey getRedisKey() {
        return RedisKey.SECTION;
    }

    @Override
    protected BaseMapper<Section> getBaseMapper() {
        return sectionDao;
    }

    @Override
    protected Class<Section> getEntityClass() {
        return Section.class;
    }

    @Override
    public List<SectionVO> getSectionList(SectionDTO sectionDTO) {
        List<SectionVO> sectionVOList = new ArrayList<>();
        try {
            Optional<BasePO> optional = sectionDTO.toPO();
            if (optional.isPresent()) {
                Section section = (Section) optional.get();
                QueryWrapper<Section> queryWrapper = new QueryWrapper<>();
                queryWrapper.setEntity(section);
                List<Section> sectionList = sectionDao.selectList(queryWrapper);
                sectionVOList = sectionList.stream()
                        .map(po -> {
                            SectionVO sectionVO = null;
                            Optional<BaseVO> baseVOptional = po.toVO();
                            if (baseVOptional.isPresent()) {
                                sectionVO = (SectionVO) baseVOptional.get();
                            }
                            return sectionVO;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("SectionSev getSectionList errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return sectionVOList;
    }
}
