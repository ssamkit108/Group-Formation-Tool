package com.dal.catmeclone.authenticationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.authenticationandauthorization.AuthenticateUserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

@SpringBootTest(classes = AuthenticationDaoTest.class)
public class AuthenticationDaoTest {
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();


	AuthenticateUserDao dbmock = abstractFactoryTest.createAuthenticationAbstractFactory().createAuthenticateUserDao();

	@Test
	void authenticateUserTest() throws UserDefinedSQLException {
		User user = new User();
		User usr = dbmock.authenticateUser(user);
		assertEquals("B00832190", usr.getBannerId());
		assertEquals("Student", usr.getUserRoles().roleName);
		assertEquals("password", usr.getPassword());

	}
}
