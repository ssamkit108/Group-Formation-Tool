package com.dal.catmeclone.notificationTest;


public class NotificationAbstractFactoryImpl implements NotificationAbstractFactoryTest {

	@Override
	public NotificationServiceMock createNotificationService() {
		// TODO Auto-generated method stub
		return new NotificationServiceMock();
	}

}
