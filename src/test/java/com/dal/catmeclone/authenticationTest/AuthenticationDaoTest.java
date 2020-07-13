package com.dal.catmeclone.authenticationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SpringBootTest(classes = AuthenticationDaoTest.class)
public class AuthenticationDaoTest {
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	AuthenticateUserDao dbmock = abstractFactoryTest.createAuthenticationAbstractFactory().createAuthenticateUserDao();

	@Test
	void authenticateUserTest() throws UserDefinedSQLException {
		User user = modelfact.createUser();
		User usr = dbmock.authenticateUser(user);
		assertEquals("B00832190", usr.getBannerId());
		assertEquals("Student", usr.getUserRoles().roleName);
		assertEquals("password", usr.getPassword());

	}
}
