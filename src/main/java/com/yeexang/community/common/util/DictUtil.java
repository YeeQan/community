package com.yeexang.community.common.util;

import com.yeexang.community.dao.DictDao;
import com.yeexang.community.pojo.po.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/8
 */
@Component
public class DictUtil {

    @Autowired
    private DictDao dictDao;

    public List<Dict> getDictList(String type) {
        Dict dict = new Dict();
        dict.setType(type);
        List<Dict> dictList = dictDao.select(dict);
        dictList.sort(Comparator.comparingInt(Dict::getSortFlag));
        return dictList;
    }
}
