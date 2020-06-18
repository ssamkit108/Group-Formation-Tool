package com.dal.catmeclone.Validation;

import java.sql.SQLException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public interface ValidationPolicy {
	public void setValue(String ruleValue);

	public abstract boolean isValid(User user) throws UserDefinedSQLException, SQLException;

	public abstract String getError();
}
