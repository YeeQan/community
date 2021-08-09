package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/8/8
 */
@Repository
public interface DictDao {

    void insert(Dict dict);

    void delete(Dict dict);

    void update(Dict dict);

    List<Dict> select(Dict dict);
}
