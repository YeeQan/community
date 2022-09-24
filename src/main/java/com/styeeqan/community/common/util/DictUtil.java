package com.styeeqan.community.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.styeeqan.community.common.constant.DictField;
import com.styeeqan.community.mapper.DictMapper;
import com.styeeqan.community.pojo.po.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 字典工具类
 *
 * @author yeeq
 * @date 2021/8/8
 */
@Component
public class DictUtil {

    @Autowired
    private DictMapper dictMapper;

    /**
     * 根据类型获取字典列表
     * @param type type
     * @return List<Dict>
     */
    public List<Dict> getDictByType(DictField.Type type) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<Dict> dictList = dictMapper.selectList(queryWrapper);
        dictList.sort(Comparator.comparingInt(Dict::getSortFlag));
        return dictList;
    }

    /**
     * 根据枚举获取字典
     * @param dict dict
     * @return Optional<Dict>
     */
    public Optional<Dict> getDict(DictField.Dict dict) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label", dict.label);
        queryWrapper.eq("type", dict.type);
        return Optional.of(dictMapper.selectOne(queryWrapper));
    }
}
