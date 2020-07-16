package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.Validation.HistoryConstraintDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryConstraintMock implements HistoryConstraintDao {

	private List<String> passwordlist = new ArrayList<String>();

	public HistoryConstraintMock() {
		passwordlist.add("Password@123");
		passwordlist.add("Password");
		passwordlist.add("Password123");
	}
	@Override
	public List<String> fetchPasswordList(User u, int limit) throws UserDefinedException, SQLException {
		return passwordlist.subList(0, limit-1);
	}
}
