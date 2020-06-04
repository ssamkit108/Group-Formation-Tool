package com.dal.catmeclone.UserProfileTest;
import java.util.*;

import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.model.*;

public class UserDaoMock{
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
}
