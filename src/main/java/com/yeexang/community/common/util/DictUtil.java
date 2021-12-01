package com.yeexang.community.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeexang.community.dao.DictDao;
import com.yeexang.community.pojo.po.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 字典工具类
 *
 * @author yeeq
 * @date 2021/8/8
 */
@Slf4j
@Component
public class DictUtil {

    @Autowired
    private DictDao dictDao;

    public List<Dict> getDictByType(String type) {
        List<Dict> dictList;
        try {
            Dict dict = new Dict();
            dict.setType(type);
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", type);
            dictList = dictDao.selectList(queryWrapper);
            dictList.sort(Comparator.comparingInt(Dict::getSortFlag));
        } catch (Exception e) {
            log.error("DictUtil getDictList errorMsg: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
        return dictList;
    }
}
