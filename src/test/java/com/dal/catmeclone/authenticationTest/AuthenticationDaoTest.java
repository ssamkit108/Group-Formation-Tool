package com.dal.catmeclone.authenticationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.dal.catmeclone.model.User;

@SpringBootTest(classes = AuthenticationDaoTest.class)
public class AuthenticationDaoTest {

	AuthenticationDaoMock dbmock = new AuthenticationDaoMock();

	@Test
	void authenticateUserTest() {
		User u = new User();
		User usr = dbmock.authenticateUser(u);
		assertEquals("B00832190", usr.getBannerId());
		assertEquals("Student", usr.getUserRoles().roleName);
		assertEquals("password", usr.getPassword());

	}
}
