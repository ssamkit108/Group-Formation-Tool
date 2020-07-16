package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.exceptionhandler.ValidationException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public boolean createUser(User u) throws ValidationException, DuplicateEntityException, SQLException, UserDefinedSQLException,Exception;

    public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException;
}
