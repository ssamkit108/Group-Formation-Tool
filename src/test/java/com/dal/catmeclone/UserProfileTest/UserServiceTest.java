package com.dal.catmeclone.UserProfileTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SpringBootTest
class UserServiceTest {
	
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	@SuppressWarnings("deprecation")
	@Test
	void CreateTest() {
		UserValidateMock check = abstractFactoryTest.createUserProfileAbstractFactory().createUserValidateMock();
		UserDaoMock DbMock = abstractFactoryTest.createUserProfileAbstractFactory().createUserDaoMock();
		User u = modelfact.createUser();		
		u.setBannerId("");
		Assert.isTrue(!check.validate(u));
		u = modelfact.createUser();		
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
		User u = modelfact.createUser();		
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
