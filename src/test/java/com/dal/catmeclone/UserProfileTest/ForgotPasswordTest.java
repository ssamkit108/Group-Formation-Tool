package com.dal.catmeclone.UserProfileTest;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SpringBootTest
@SuppressWarnings("deprecation")
class ForgotPasswordTest {
	
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	
	UserDaoMock dbservice= abstractFactoryTest.createUserProfileAbstractFactory().createUserDaoMock();
	@Test
	void checkexistTest() {
		User u = modelfact.createUser();		
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
		User u = modelfact.createUser();	
		u.setBannerId("B00825292");
		u.setEmail("bob123@gmail.com");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		u.setPassword("password");
		dbservice.UpdatePassword(u.getBannerId(), u.getPassword());
		
		Assert.isTrue(!u.getPassword().equals("12345"));
		Assert.isTrue(u.getPassword().equals("password"));
	}
	
	@Test
	void checktokenExist() {
		User u = modelfact.createUser();		
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
