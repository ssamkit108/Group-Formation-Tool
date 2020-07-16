package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;

import java.util.logging.Logger;


public class MinimumLength implements ValidationPolicy {

    final Logger LOGGER = Logger.getLogger(MinimumLength.class.getName());
    private String ruleValue;

    @Override
    public void setValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    @Override
    public boolean isValid(User user) {
        String password = user.getPassword();
        boolean result = password.length() >= Integer.parseInt(this.ruleValue);
        LOGGER.info("Password Minimum length validation. Result : " + result);
        return result;
    }

    @Override
    public String getError() {
        return "Password must be at least " + this.ruleValue + " characters long.";
    }

}
