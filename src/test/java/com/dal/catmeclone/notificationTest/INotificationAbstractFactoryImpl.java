package com.dal.catmeclone.notificationTest;


public class INotificationAbstractFactoryImpl implements INotificationAbstractFactory {

	@Override
	public NotificationServiceMock createNotificationService() {
		// TODO Auto-generated method stub
		return new NotificationServiceMock();
	}

}
