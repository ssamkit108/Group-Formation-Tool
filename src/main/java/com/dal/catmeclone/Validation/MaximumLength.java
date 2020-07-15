package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaximumLength implements ValidationPolicy {

    final Logger LOGGER = LoggerFactory.getLogger(MaximumLength.class);
    private String ruleValue;

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
