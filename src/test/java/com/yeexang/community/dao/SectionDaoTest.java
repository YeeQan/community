package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.Section;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author yeeq
 * @date 2021/7/21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SectionDaoTest {

    @Autowired
    private SectionDao sectionDao;

    @Test
    public void testInsert() {
        Section section = new Section();
        section.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        section.setSectionId("123456");
        section.setSectionName("test");
        section.setCreateTime(new Date());
        section.setCreateUser("123456");
        section.setUpdateTime(new Date());
        section.setUpdateUser("123456");
        section.setDelFlag(false);
        sectionDao.insert(section);
    }

    @Test
    public void testDelete() {
        Section section = new Section();
        section.setSectionId("123456");
        sectionDao.delete(section);
    }

    @Test
    public void testUpdate() {
        Section section = new Section();
        section.setSectionId("123456");
        section.setSectionName("test2");
        sectionDao.update(section);
    }

    @Test
    public void testSelect() {
        Section section = new Section();
        List<Section> select = sectionDao.select(section);
        for (Section section1 : select) {
            log.info("SectionDaoTest testSelect : {}", section1.toString());
        }
    }
}
