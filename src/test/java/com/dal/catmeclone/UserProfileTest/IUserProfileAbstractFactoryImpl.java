package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;

public class IUserProfileAbstractFactoryImpl implements IUserProfileAbstractFactory {

    @Override
    public BCryptPasswordEncryption createBCryptPasswordEncryption() {
        // TODO Auto-generated method stub
        return new BCryptPasswordEncryptionMock();
    }

    @Override
    public UserDaoMock createUserDaoMock() {
        // TODO Auto-generated method stub
        return new UserDaoMock();
    }

    @Override
    public UserValidateMock createUserValidateMock() {
        // TODO Auto-generated method stub
        return new UserValidateMock();
    }

    @Override
    public NotificationMock createNotificationMock() {
        // TODO Auto-generated method stub
        return new NotificationMock();
    }

}
