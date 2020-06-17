package com.dal.catmeclone.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dal.catmeclone.model.User;

public class MinimumLength implements ValidationPolicy {

	private String ruleValue;
	final Logger logger = LoggerFactory.getLogger(MinimumLength.class);
	
	@Override
	public void setValue(String ruleValue) {
		// TODO Auto- method stub
		this.ruleValue=ruleValue;
	}

	@Override
	public boolean isValid(User user) {
		// TODO Auto-generated method stub
		String password=user.getPassword();
		boolean result = password.length() >= Integer.parseInt(this.ruleValue);
		logger.info("Password length validation. Result : " + result);
		return result;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return "Password must be at least "  + this.ruleValue + " characters long";
	}

}
