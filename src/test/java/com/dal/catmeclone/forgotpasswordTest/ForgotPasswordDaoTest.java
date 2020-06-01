package com.dal.catmeclone.forgotpasswordTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.CreateUserTest.UserDaoMock;
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
	void UpdatePasswordTest() {
		String bannerId="B00243453";
		String password="Test@123";
		
		Assert.isTrue(dbservice.UpdatePassword(bannerId,password));
		
	}
}
