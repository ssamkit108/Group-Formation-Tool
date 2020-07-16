package com.dal.catmeclone.authenticationTest;

import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactory;
import com.dal.catmeclone.authenticationandauthorization.SuccessHandler;
import com.dal.catmeclone.authenticationandauthorization.UserAuthentication;

public class AuthenticationAbstractFactoryTestImpl implements AuthenticationAbstractFactory {


    @Override
    public SuccessHandler createSuccessHandler() {
        return new SuccessHandler();
    }

    @Override
    public UserAuthentication createUserAuthentication() {
        return new UserAuthentication();
    }

    @Override
    public AuthenticateUserDao createAuthenticateUserDao() {
        return new AuthenticationDaoMock();
    }

    @Override
    public UserAuthentication createUserAuthentication(AuthenticateUserDao authenticateUserDao) {
        return new UserAuthentication(authenticateUserDao);
    }

}
