package com.dal.catmeclone.authenticationTest;

import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;

public interface IAuthenticationAbstractFactory {
    public AuthenticateUserDao createAuthenticateUserDao();
}
