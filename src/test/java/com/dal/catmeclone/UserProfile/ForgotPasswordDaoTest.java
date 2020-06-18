package com.dal.catmeclone.UserProfile;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.User;

@SpringBootTest
@SuppressWarnings("deprecation")
class ForgotPasswordDaoTest {
	
	UserDaoMock dbservice=new UserDaoMock();
	@Test
	void checkexistTest() {
		User u=new User();
		u.setBannerId("B00642283");
		Assert.isTrue(dbservice.findUserByBannerID(u.getBannerId()));
	}
	
	@Test
	void UpdatetokenTest() {
		String bannerId="B00243453";
		String token="$10$P2FBvrlVJaS1/aEHzfKmtuojZollAx/F1g0DnvO7QvNv7/AUyihDu";
		
		Assert.isTrue(dbservice.UpdatePassword(bannerId,token));
		
	}
	
	@Test
	void SetNewPassword() {
		User u=new User();
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		//Now we need to change the password of user
		u.setPassword("password");
		dbservice.UpdatePassword(u.getBannerId(), u.getPassword());
		
		Assert.isTrue(!u.getPassword().equals("12345"));
		Assert.isTrue(u.getPassword().equals("password"));
	}
	
	@Test
	void checktokenExist() {
		User u=new User();
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		u.setToken("");

		Assert.isTrue(!u.gettoken().equals("d sandaslk"));
		
		u.setToken("1234kfmnakfnafnaklnflkanfnklnkflnasklfna");
		
		Assert.isTrue(u.gettoken().equals("1234kfmnakfnafnaklnflkanfnklnkflnasklfna"));
		
	}
}
