package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface UserService {
    public boolean createUser(User u) throws Exception;

    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
}
