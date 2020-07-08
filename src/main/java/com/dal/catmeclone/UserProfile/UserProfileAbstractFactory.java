package com.dal.catmeclone.UserProfile;

public interface UserProfileAbstractFactory {
    public UserService createUserService();
    public UserDao createUserDao();
    public ForgotPasswordDao createForgotPasswordDao();
    public ForgotPasswordService createForgotPasswordService();
}
