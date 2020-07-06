package com.dal.catmeclone.authenticationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.User;

@SpringBootTest(classes = AuthenticationDaoTest.class)
public class AuthenticationDaoTest {
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();


	AuthenticationDaoMock dbmock = new AuthenticationDaoMock();

	@Test
	void authenticateUserTest() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		User usr = dbmock.authenticateUser(user);
		assertEquals("B00832190", usr.getBannerId());
		assertEquals("Student", usr.getUserRoles().roleName);
		assertEquals("password", usr.getPassword());

	}
}
