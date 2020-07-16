package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public boolean createUser(User u) throws SQLException, Exception;

    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedException;
}
