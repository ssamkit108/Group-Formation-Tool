package com.dal.catmeclone.authenticationTest;

import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;

public class IAuthenticationAbstractFactoryImpl implements IAuthenticationAbstractFactory{

	@Override
	public AuthenticateUserDao createAuthenticateUserDao() {
		// TODO Auto-generated method stub
		return new AuthenticationDaoMock();
	}

}
