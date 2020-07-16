package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;

import java.util.logging.Logger;


public class MinimumSpecial implements ValidationPolicy {

    final Logger LOGGER = Logger.getLogger(MinimumSpecial.class.getName());
    String ruleValue = "";

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
