package com.dal.catmeclone.UserProfile;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public interface UserService {
	
	public boolean Create(User u);
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
	
}
