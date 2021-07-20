package com.yeexang.community.dao;

import com.yeexang.community.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/20
 */
@Repository
public interface UserDao {

    void insert(User user);

    void delete(User user);

    void update(User user);

    List<User> select(User user);
}
