package com.dal.catmeclone.UserProfileTest;

import java.util.*;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

public class UserDaoMock{
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();

	
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

	public boolean createUser(User user)
	{	
		List<User> users=new ArrayList<User>();
		users.add(user);
		return true;
	}
	public boolean findUserByBannerID(String username) {
		return true;
	}
	
	public boolean UpdatePassword(String username,String password) {
		return true;
	}
	
	public List<User> getAllUsers() {
		List<User> l = new ArrayList<User>();
		User u = modelfact.createUser();
		u.setBannerId("B00825292");
		u.setFirstName("Bob");
		u.setLastName("Shaw");
		u.setPassword("12345");
		u.setEmail("bob123@gmail.com");
		l.add(u);
		return l;
	}

}
