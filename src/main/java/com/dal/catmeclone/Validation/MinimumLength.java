package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumLength implements ValidationPolicy {

    final Logger LOGGER = LoggerFactory.getLogger(MinimumLength.class);
    private String ruleValue;

    @Override
    public void setValue(String ruleValue) {
        // TODO Auto- method stub
        this.ruleValue = ruleValue;
    }

    @Override
    public boolean isValid(User user) {
        // TODO Auto-generated method stub
        String password = user.getPassword();
        boolean result = password.length() >= Integer.parseInt(this.ruleValue);
        LOGGER.info("Password Minimum length validation. Result : " + result);
        return result;
    }

    @Override
    public String getError() {
        // TODO Auto-generated method stub
        return "Password must be at least " + this.ruleValue + " characters long.";
    }

}
