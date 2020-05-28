package com.dal.catmeclone.CreateUser;
import java.sql.SQLException;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

public interface Interface_CreateUser {
	
	public boolean createUser(User user) throws SQLException, UserDefinedSQLException;
}
