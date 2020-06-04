package com.dal.catmeclone.UserProfileTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.User;

@SpringBootTest
@SuppressWarnings("deprecation")
class ForgotPasswordServiceTest {

	
	private static final String ALPHA_NUMERIC_STRING = "qwertyuiopkljhgfdsazxcvbnm0123456789!@#$%^&*()";

	
	@Test
	void forgotpasswordTest() { 
	NotificationMock email= new NotificationMock();
	Assert.isTrue(email.sendemail("B00852292", "csci5409"));
	}
				
	@Test
	void ValidateUserTest() {	
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
	
	@Test
	void GeneratePasswordTest() {

		StringBuilder builder = new StringBuilder();
		builder.setLength(0);

		for(int i=0;i<8;i++)
		{
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		String new_password= builder.toString();
		
		Assert.isTrue(!new_password.equals("password"));
	}
}
