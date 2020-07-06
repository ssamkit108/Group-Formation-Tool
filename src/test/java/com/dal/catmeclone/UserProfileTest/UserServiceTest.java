package com.dal.catmeclone.UserProfileTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.User;

@SpringBootTest
class UserServiceTest {
	
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();


	@SuppressWarnings("deprecation")
	@Test
	void CreateTest() {
		UserValidateMock check = new UserValidateMock();
		UserDaoMock DbMock = new UserDaoMock();
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setBannerId("");
		Assert.isTrue(!check.validate(u));
		u = abstractFactory.createModelAbstractFactory().createUser();		
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
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		UserDaoMock Dbmock = new UserDaoMock();
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
