package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

public interface AuthenticateUserDao {
	public User authenticateUser(User user)throws UserDefinedException ;
}
