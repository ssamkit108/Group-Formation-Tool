package com.dal.catmeclone.UserProfile;


import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

public interface UserDao {

	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException;
	public boolean createUser(User student) throws UserDefinedSQLException, DuplicateEntityException;
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException;


}
