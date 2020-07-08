package com.dal.catmeclone.UserProfile;

public class UserProfileAbstractFactoryImpl implements UserProfileAbstractFactory{
    @Override
    public UserService createUserService() {
        return new UserServiceImpl();
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public ForgotPasswordDao createForgotPasswordDao() {
        return new ForgotPasswordDaoImpl();
    }

    @Override
    public ForgotPasswordService createForgotPasswordService() {
        return new ForgotPasswordServiceImpl();
    }
}
