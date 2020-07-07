package com.dal.catmeclone.authenticationTest;

import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;

public class AuthenticationAbstractFactoryImpl implements AuthenticationAbstractFactoryTest{

	@Override
	public AuthenticateUserDao createAuthenticateUserDao() {
		// TODO Auto-generated method stub
		return new AuthenticationDaoMock();
	}

}
