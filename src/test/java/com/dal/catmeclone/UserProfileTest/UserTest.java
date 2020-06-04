package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.model.*;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest
{
		
	@Test
	public void setBannerIDTest()
	{
		User u = new User();
		u.setBannerId("B00854796");
		Assert.isTrue(u.getBannerId().equals("B00854796"));
	}
	
	@Test
	public void getBannerIDTest()
	{
		User u = new User();
		u.setBannerId("B00854796");
		Assert.isTrue(u.getBannerId().equals("B00854796"));
	}
	
	@Test
	public void setFirstNameTest()
	{
		User u = new User();
		u.setFirstName("Bob");
		Assert.isTrue(u.getFirstName().equals("Bob"));
	}
	
	@Test
	public void getFirstNameTest()
	{
		User u = new User();
		u.setFirstName("Bob");
		Assert.isTrue(u.getFirstName().equals("Bob"));
	}

	@Test
	public void setLastNameTest()
	{
		User u = new User();
		u.setLastName("Mcallister");
		Assert.isTrue(u.getLastName().equals("Mcallister"));
	}

	@Test
	public void getLastNameTest()
	{
		User u = new User();
		u.setLastName("Mcallister");
		Assert.isTrue(u.getLastName().equals("Mcallister"));
	}
	
	@Test
	public void setEmailTest()
	{
		User u = new User();
		u.setEmail("bob@gmail.com");
		Assert.isTrue(u.getEmail().equals("bob@gmail.com"));
	}
	
	@Test
	public void getEmailTest()
	{
		User u = new User();
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}
	
	
	@Test
	public void isEmailValidTest()
	{
		Assert.isTrue(User.isEmailValid("bob@gmail.com"));
		Assert.isTrue(!User.isEmailValid(null));
		Assert.isTrue(!User.isEmailValid(""));
		Assert.isTrue(!User.isEmailValid("ssss"));
		Assert.isTrue(!User.isEmailValid("@gma.com"));
	}	
}
