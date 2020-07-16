package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.UserProfile.*;

public class UserProfileAbstractFactoryTestImpl implements UserProfileAbstractFactory {

    @Override
    public UserService createUserService() {

        return new UserServiceImpl();
    }

    @Override
    public UserService createUserService(UserDao userDao) {

        return new UserServiceImpl(userDao);
    }

    @Override
    public UserDao createUserDao() {

        return new UserDaoMock();
    }

    @Override
    public ForgotPasswordDao createForgotPasswordDao() {

        return new ForgotPasswordDaoImpl();
    }

    @Override
    public ForgotPasswordService createForgotPasswordService() {

        return new ForgotPasswordServiceImpl();
    }

    @Override
    public ForgotPasswordService createForgotPasswordService(ForgotPasswordDao forgotPasswordDao) {

        return new ForgotPasswordServiceImpl(forgotPasswordDao);
    }

}
