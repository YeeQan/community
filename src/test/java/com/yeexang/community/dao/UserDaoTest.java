package com.yeexang.community.dao;

import com.yeexang.community.pojo.po.User;
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
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setAccount("123456");
        user.setUsername("testUser");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        user.setCreateUser("123456");
        user.setUpdateTime(new Date());
        user.setUpdateUser("123456");
        user.setDelFlag(false);
        userDao.insert(user);
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setAccount("123456");
        userDao.delete(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setAccount("123456");
        user.setUsername("testUser2");
        userDao.update(user);
    }

    @Test
    public void testSelect() {
        User user = new User();
        List<User> select = userDao.select(user);
        for (User user1 : select) {
            log.info("UserDaoTest testSelect : {}", user1.toString());
        }
    }
}
