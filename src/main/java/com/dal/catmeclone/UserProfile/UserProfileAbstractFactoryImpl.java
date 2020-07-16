package com.dal.catmeclone.UserProfile;

public class UserProfileAbstractFactoryImpl implements UserProfileAbstractFactory {
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

    @Override
    public UserService createUserService(UserDao userDao) {
        return new UserServiceImpl(userDao);
    }

    @Override
    public ForgotPasswordService createForgotPasswordService(ForgotPasswordDao forgotPasswordDao) {
        return new ForgotPasswordServiceImpl(forgotPasswordDao);
    }
}
