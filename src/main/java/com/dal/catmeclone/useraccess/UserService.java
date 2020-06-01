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
public interface UserService {
	
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
	
}
