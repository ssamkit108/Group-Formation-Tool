package com.dal.catmeclone.UserProfileTest;

import java.util.*;

import com.dal.catmeclone.model.*;

public class UserDaoMock{
	String bannerid;
	String email;
	String firstname;
	String lastname;
	String password;
	ArrayList<User> userList = new ArrayList<User>();

	public UserDaoMock() {
		super();
		
		this.bannerid = "B00825292";
		this.email = "bob123@gmail.com";
		this.firstname = "Bob";
		this.lastname = "Shaw";
		this.password= "12345";
		userList.add(new User(bannerid, email,firstname,lastname,password));

	}
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
		l.add(new User("B00825292", "Bob", "Shaw", "12345", "bob123@gmail.com"));
		return l;
	}


}
