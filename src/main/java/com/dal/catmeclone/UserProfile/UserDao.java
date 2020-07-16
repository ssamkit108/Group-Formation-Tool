package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface UserDao {
    public User findUserByBannerID(String bannerId) throws UserDefinedException;

    public boolean createUser(User student) throws UserDefinedException, DuplicateEntityException;

    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedException;

    public List<User> getAllUsers() throws UserDefinedException, Exception;

}
