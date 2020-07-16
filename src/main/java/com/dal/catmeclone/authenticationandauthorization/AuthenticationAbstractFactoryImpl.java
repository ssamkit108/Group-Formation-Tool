package com.dal.catmeclone.authenticationandauthorization;

public class AuthenticationAbstractFactoryImpl implements AuthenticationAbstractFactory {

    @Override
    public AuthenticateUserDao createAuthenticateUserDao() {
        return new AuthenticateUserDaoImpl();
    }

    @Override
    public SuccessHandler createSuccessHandler() {
        return new SuccessHandler();
    }

    @Override
    public UserAuthentication createUserAuthentication() {
        return new UserAuthentication();
    }

    @Override
    public UserAuthentication createUserAuthentication(AuthenticateUserDao authenticateUserDao) {
        return new UserAuthentication(authenticateUserDao);
    }

}
