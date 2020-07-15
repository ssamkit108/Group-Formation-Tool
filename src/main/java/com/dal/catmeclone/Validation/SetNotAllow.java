package com.dal.catmeclone.Validation;

import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SetNotAllow implements ValidationPolicy {

    final Logger LOGGER = LoggerFactory.getLogger(SetNotAllow.class);
    String ruleValue = "";

    @Override
    public void setValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    @Override
    public boolean isValid(User user) {
        String password = user.getPassword();
        ArrayList<Character> characters = new ArrayList<Character>();
        for (char ch : this.ruleValue.toCharArray()) {
            characters.add(ch);
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (characters.contains(ch)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public String getError() {
        return "Password must not contain  " + this.ruleValue + " Set of Characters.";
    }
}
