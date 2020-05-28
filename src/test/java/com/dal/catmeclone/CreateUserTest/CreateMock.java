package com.dal.catmeclone.CreateUserTest;
import com.dal.catmeclone.CreateUser.Interface_CreateUser;
import com.dal.catmeclone.model.*;

public class CreateMock implements Interface_CreateUser {
	
	public boolean createUser(User user)
	{
		return true;
	}
}
