package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Section;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 专栏管理 Dao
 *
 * @author yeeq
 * @date 2021/7/20
 */
@Repository
public interface SectionDao {

    void insert(Section section);

    void delete(Section section);

    void update(Section section);

    List<Section> select(Section section);
}
