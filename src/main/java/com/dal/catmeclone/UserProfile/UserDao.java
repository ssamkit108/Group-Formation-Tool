package com.dal.catmeclone.UserProfile;

import java.util.List;
import com.dal.catmeclone.exceptionhandler.DuplicateUserRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

public interface UserDao {
	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException;

	public boolean createUser(User student) throws UserDefinedSQLException, DuplicateUserRelatedException;

	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
}
