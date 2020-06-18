package com.dal.catmeclone.authenticationandauthorization;

import java.sql.SQLException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public interface AuthenticateUserDao {
	public User authenticateUser(User user) throws UserDefinedSQLException;
}
