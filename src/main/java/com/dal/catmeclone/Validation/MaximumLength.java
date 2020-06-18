package com.dal.catmeclone.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.model.User;

public class MaximumLength implements ValidationPolicy {

	private String ruleValue;
	final Logger LOGGER = LoggerFactory.getLogger(MaximumLength.class);

	@Override
	public void setValue(String ruleValue) {
		this.ruleValue = ruleValue;

	}

	@Override
	public boolean isValid(User user) {
		String password = user.getPassword();
		boolean result = password.length() <= Integer.parseInt(this.ruleValue);
		LOGGER.info("Password Maximum length validation. Result : " + result);
		return result;
	}

	@Override
	public String getError() {
		return "Password should not greater than " + this.ruleValue + " character.";
	}

}
