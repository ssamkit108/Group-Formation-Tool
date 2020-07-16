package com.dal.catmeclone.notificationTest;

import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notification.NotificationService;
import com.dal.catmeclone.notification.NotificationServiceImpl;

public class NotificationAbstractFactoryTestImpl implements NotificationAbstractFactory {

    @Override
    public NotificationService createNotificationService() {
        return new NotificationServiceImpl();
    }

}
