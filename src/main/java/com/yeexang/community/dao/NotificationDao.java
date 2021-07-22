package com.yeexang.community.dao;

import com.yeexang.community.entity.Notification;
import com.yeexang.community.entity.Section;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yeeq
 * @date 2021/7/20
 */
@Repository
public interface NotificationDao {

    void insert(Notification notification);

    void delete(Notification notification);

    void update(Notification notification);

    List<Notification> select(Notification notification);
}
