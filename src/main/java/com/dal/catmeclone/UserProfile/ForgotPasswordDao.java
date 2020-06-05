package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public interface ForgotPasswordDao {
	public boolean checkexist(String username) throws UserDefinedSQLException, Exception;
	public void UpdatePassword(String BannerId,String password) throws Exception;
}
