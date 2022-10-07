package com.styeeqan.community.web.service;

import com.styeeqan.community.mapper.TagMapper;
import com.styeeqan.community.pojo.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TagSev {

    @Autowired
    private TagMapper tagMapper;

    public List<TagVO> getTagList() {
        return tagMapper.selectList(null).stream().map(tag -> {
            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setName(tag.getName());
            return tagVO;
        }).collect(Collectors.toList());
    }
}
