package com.dal.catmeclone.Validation;

import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public interface HistoryContraintDao {
	public List<String> fetchPasswordList(User u,int limit) throws UserDefinedSQLException,SQLException;

}
