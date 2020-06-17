package com.dal.catmeclone.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dal.catmeclone.model.User;

public class MinimumUpper implements ValidationPolicy {

	
	private String ruleValue;
	final Logger logger = LoggerFactory.getLogger(MinimumUpper.class);

	@Override
	public void setValue(String ruleValue) {
		this.ruleValue=ruleValue;	

	}

	@Override
	public boolean isValid(User user) {
		String password=user.getPassword();
		int count=0;
		for(int i=0;i<password.length();i++) {
			if(Character.isUpperCase(password.charAt(i))) {
				count++;
			}
		}
		boolean result = count >= Integer.parseInt(this.ruleValue);
		logger.info("Password UpperCase validation. Result : " + result);
		return result;
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return "Password must contain at least "+this.ruleValue+" Uppercase letter.";
	}

}
