package com.dal.catmeclone.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dal.catmeclone.model.User;

public class MinimumSpecial implements ValidationPolicy {

	String ruleValue = "";
	final Logger logger = LoggerFactory.getLogger(MinimumSpecial.class);

	@Override
	public void setValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	@Override
	public boolean isValid(User user) {

		String password = user.getPassword();
		int count = 0;
		for (int i = 0; i < password.length(); i++) {
			char ch = password.charAt(i);
			if (!(ch >= 'A' && ch <= 'Z') && !(ch >= 'a' && ch <= 'z') && !(ch >= '0' && ch <= '9')) {
				count++;
			}
		}
		boolean result = count >= Integer.parseInt(this.ruleValue);
		return result;
	}

	@Override
	public String getError() {
		return "Password must contain at least " + this.ruleValue + " Special Characters.";

	}
}
