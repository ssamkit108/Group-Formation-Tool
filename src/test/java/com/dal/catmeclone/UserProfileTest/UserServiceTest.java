package com.dal.catmeclone.UserProfileTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.User;

@SpringBootTest
class UserServiceTest {
	
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();


	@SuppressWarnings("deprecation")
	@Test
	void CreateTest() {
		UserValidateMock check = abstractFactoryTest.createUserProfileAbstractFactory().createUserValidateMock();
		UserDaoMock DbMock = abstractFactoryTest.createUserProfileAbstractFactory().createUserDaoMock();
		User u = new User();		
		u.setBannerId("");
		Assert.isTrue(!check.validate(u));
		u = new User();		
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		Assert.isTrue(check.validate(u));
		Assert.isTrue(DbMock.createUser(u));
	}

	@Test
	public void GetAllUsersTest() {
		User u = new User();		
		UserDaoMock Dbmock = abstractFactoryTest.createUserProfileAbstractFactory().createUserDaoMock();

		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		List<User> li = new ArrayList<User>();
		li.add(u);
		assertEquals(Dbmock.getAllUsers(), li);
	}
}
