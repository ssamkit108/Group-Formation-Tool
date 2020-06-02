package com.dal.catmeclone.UserProfile;
import java.sql.SQLException;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

public interface UserDao {
	
	public boolean createUser(User user) throws SQLException, UserDefinedSQLException;

}
