package com.dal.catmeclone.UserProfile;

public interface UserProfileAbstractFactory {

    public UserService createUserService();

    public UserService createUserService(UserDao userDao);

    public UserDao createUserDao();

    public ForgotPasswordDao createForgotPasswordDao();

    public ForgotPasswordService createForgotPasswordService();

    public ForgotPasswordService createForgotPasswordService(ForgotPasswordDao forgotPasswordDao);

}
