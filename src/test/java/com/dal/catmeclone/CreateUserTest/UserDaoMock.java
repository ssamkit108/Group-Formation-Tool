package com.dal.catmeclone.CreateUserTest;
import com.dal.catmeclone.UserSignup.UserDao;
import com.dal.catmeclone.model.*;

public class UserDaoMock{
	
	public boolean createUser(User user)
	{
		return true;
	}
	public boolean findUserByBannerID(String username) {
		return true;
	}
	
	public boolean UpdatePassword(String username,String password) {
		return true;
	}
}
