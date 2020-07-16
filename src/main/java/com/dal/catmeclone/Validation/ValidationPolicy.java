package com.dal.catmeclone.Validation;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;

public interface ValidationPolicy {
    public void setValue(String ruleValue);

    public abstract boolean isValid(User user) throws UserDefinedException, SQLException, Exception;

    public abstract String getError();
}
