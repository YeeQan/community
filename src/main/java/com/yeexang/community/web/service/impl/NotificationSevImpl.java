package com.yeexang.community.web.service.impl;

import com.yeexang.community.web.service.NotificationSev;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yeeq
 * @date 2021/7/29
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationSevImpl implements NotificationSev {

    /*@Autowired
    private NotificationDao notificationDao;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public void setNotify(NotificationDTO notificationDTO) {
        try {
            Optional<BasePO> optional = notificationDTO.toPO();
            if (optional.isPresent()) {
                Notification notification = (Notification) optional.get();
                notification.setId(commonUtil.uuid());
                String notificationId = commonUtil.randomCode();
                notification.setNotificationId(notificationId);
                notification.setNotificationId(notificationId);
                notification.setStatus(false);
                notification.setCreateTime(new Date());
                notification.setCreateUser(notificationDTO.getNotifier());
                notification.setUpdateTime(new Date());
                notification.setUpdateUser(notificationDTO.getNotifier());
                notification.setDelFlag(false);
                notificationDao.insert(notification);
            }
        } catch (Exception e) {
            log.error("NotificationSev setNotify errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public List<Notification> receive(NotificationDTO notificationDTO) {
        List<Notification> notificationList = new ArrayList<>();
        try {
            Optional<BasePO> optional = notificationDTO.toPO();
            if (optional.isPresent()) {
                Notification notification = (Notification) optional.get();
                List<Notification> notificationDBList = notificationDao.select(notification);
                if (!notificationDBList.isEmpty()) {
                    notificationList.addAll(notificationDBList);
                }
            }
        } catch (Exception e) {
            log.error("NotificationSev receive errorMsg: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ArrayList<>();
        }
        return notificationList;
    }*/
}