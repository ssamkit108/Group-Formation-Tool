package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumLower implements ValidationPolicy {

    final Logger LOGGER = LoggerFactory.getLogger(MinimumLower.class);
    private String ruleValue;

    @Override
    public void setValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    @Override
    public boolean isValid(User user) {
        String password = user.getPassword();
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                count++;
            }
        }
        boolean result = count >= Integer.parseInt(this.ruleValue);
        LOGGER.info("Password Minimum lowercase validation. Result : " + result);
        return result;
    }

    @Override
    public String getError() {
        return "Password must contain at least " + this.ruleValue + " lowercase letter.";
    }

}
