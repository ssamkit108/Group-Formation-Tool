package com.dal.catmeclone.CreateUserTest;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.User;

@SpringBootTest
class UserServiceTest {

	
	
	@SuppressWarnings("deprecation")
	@Test
	void CreateTest() {
		UserValidateMock check=new UserValidateMock();

		User u=new User();
		u.setBannerId("");
		Assert.isTrue(!check.validate(u));
		
		
		u=new User();
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		Assert.isTrue(check.validate(u));

		
	}

}
