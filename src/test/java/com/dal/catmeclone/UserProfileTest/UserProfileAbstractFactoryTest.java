package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;

public interface UserProfileAbstractFactoryTest {
	public BCryptPasswordEncryption createBCryptPasswordEncryption();
	public UserDaoMock createUserDaoMock();
	public UserValidateMock createUserValidateMock();
	public NotificationMock createNotificationMock();
}
