package com.dal.catmeclone.UserProfile;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.*;


@SpringBootTest
public class UserDaoTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void CreateUserTest()
	{
		User u = new User();
		UserDaoMock mock=new UserDaoMock();
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		Assert.isTrue(mock.createUser(u));
	}
	
	@Test
	public void GetAllUsersTest() {
		User u = new User();
		UserDaoMock mock = new UserDaoMock();
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
