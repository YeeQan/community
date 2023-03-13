package com.styeeqan.community.web.service;

import com.styeeqan.community.common.redis.RedisKey;
import com.styeeqan.community.common.redis.RedisUtil;
import com.styeeqan.community.mapper.TagMapper;
import com.styeeqan.community.pojo.po.Tag;
import com.styeeqan.community.pojo.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TagSev {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisUtil redisUtil;

    public List<TagVo> getTagList() {
        return tagMapper.selectList(null).stream().map(tag -> {
            TagVo tagVO = new TagVo();
            tagVO.setId(tag.getId());
            tagVO.setName(tag.getName());
            return tagVO;
        }).collect(Collectors.toList());
    }

    public List<TagVo> getHotTagList() {
        Map<Object, Object> map = redisUtil.getHashMap(RedisKey.TAG_HOT_HASH);
        List<String> hotTagKeys = map.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().toString().compareTo(entry1.getValue().toString()))
                .map(entry -> entry.getKey().toString())
                .collect(Collectors.toList());
        if (hotTagKeys.size() > 20) {
            hotTagKeys = hotTagKeys.subList(0, 20);
        }
        return hotTagKeys.stream().map(key -> {
            Tag tag = tagMapper.selectById(key);
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            return tagVo;
        }).collect(Collectors.toList());
    }
}
