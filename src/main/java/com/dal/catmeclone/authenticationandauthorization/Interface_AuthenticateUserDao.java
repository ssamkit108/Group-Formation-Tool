package com.dal.catmeclone.authenticationandauthorization;

import java.sql.SQLException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public interface Interface_AuthenticateUserDao {
	public User authenticateUser(User user) throws SQLException, UserDefinedSQLException;
}
