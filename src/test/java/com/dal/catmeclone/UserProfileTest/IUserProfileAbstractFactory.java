package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;

public interface IUserProfileAbstractFactory {
	public BCryptPasswordEncryption createBCryptPasswordEncryption();
	public UserDaoMock createUserDaoMock();
	public UserValidateMock createUserValidateMock();
	public NotificationMock createNotificationMock();
}
