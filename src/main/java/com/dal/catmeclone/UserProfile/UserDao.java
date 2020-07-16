package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface UserDao {
    public User findUserByBannerID(String bannerId) throws UserDefinedSQLException;

    public boolean createUser(User student) throws UserDefinedSQLException, DuplicateEntityException;

    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;

    public List<User> getAllUsers() throws UserDefinedSQLException, Exception;

}
