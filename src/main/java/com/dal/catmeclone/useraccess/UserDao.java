/**
 * 
 */
package com.dal.catmeclone.useraccess;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

/**
 * @author Mayank
 *
 */
public interface UserDao {
	
	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException;
	public boolean createUser(User student) throws UserDefinedSQLException;
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
	
	
	
}
