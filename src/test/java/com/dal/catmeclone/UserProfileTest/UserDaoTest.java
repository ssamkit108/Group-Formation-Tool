package com.dal.catmeclone.UserProfileTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SpringBootTest
public class UserDaoTest {
	
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	UserDaoMock mock = abstractFactoryTest.createUserProfileAbstractFactory().createUserDaoMock();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	@SuppressWarnings("deprecation")
	@Test
	public void CreateUserTest() {
		User u = modelfact.createUser();		
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		Assert.isTrue(mock.createUser(u));
	}

	@Test
	public void GetAllUsersTest() {
		User u = modelfact.createUser();		
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		List<User> li = new ArrayList<User>();
		li.add(u);
		assertEquals(mock.getAllUsers(), li);
	}

}
