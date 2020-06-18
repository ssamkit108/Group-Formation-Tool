package com.dal.catmeclone.ValidationTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

public class HistoryConstraintMock {

	private String ruleValue;
	private List<String> passwordlist = new ArrayList<String>();

	public HistoryConstraintMock() {
		passwordlist.add("Password@123");
		passwordlist.add("Password");
		passwordlist.add("Password123");
	}

	public void setValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public boolean isValid(User user) throws UserDefinedSQLException, SQLException {
		boolean result = false;
		result = !(passwordlist.contains(user.getPassword()));
		return result;
	}

	public String getError() {
		return "Password cannot be same as past " + this.ruleValue + " passwords";
	}
}
