package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.*;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest
{
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

		
	@Test
	public void setBannerIDTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setBannerId("B00854796");
		Assert.isTrue(u.getBannerId().equals("B00854796"));
	}
	
	@Test
	public void getBannerIDTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setBannerId("B00854796");
		Assert.isTrue(u.getBannerId().equals("B00854796"));
	}
	
	@Test
	public void setFirstNameTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setFirstName("Bob");
		Assert.isTrue(u.getFirstName().equals("Bob"));
	}
	
	@Test
	public void getFirstNameTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setFirstName("Bob");
		Assert.isTrue(u.getFirstName().equals("Bob"));
	}

	@Test
	public void setLastNameTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setLastName("Mcallister");
		Assert.isTrue(u.getLastName().equals("Mcallister"));
	}

	@Test
	public void getLastNameTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setLastName("Mcallister");
		Assert.isTrue(u.getLastName().equals("Mcallister"));
	}
	
	@Test
	public void setEmailTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setEmail("bob@gmail.com");
		Assert.isTrue(u.getEmail().equals("bob@gmail.com"));
	}
	
	@Test
	public void getEmailTest()
	{
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}
	
	
}
