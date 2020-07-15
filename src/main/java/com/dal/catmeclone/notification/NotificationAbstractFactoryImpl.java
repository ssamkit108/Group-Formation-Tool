package com.dal.catmeclone.notification;

public class NotificationAbstractFactoryImpl implements NotificationAbstractFactory {
    public NotificationService createNotificationService() {
        return new NotificationServiceImpl();
    }
}
