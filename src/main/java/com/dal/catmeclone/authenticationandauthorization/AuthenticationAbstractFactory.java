package com.dal.catmeclone.authenticationandauthorization;

public interface AuthenticationAbstractFactory {
    public AuthenticateUserDao createAuthenticateUserDao();

    public SuccessHandler createSuccessHandler();

    public UserAuthentication createUserAuthentication();

    public UserAuthentication createUserAuthentication(AuthenticateUserDao authenticateUserDao);
}
