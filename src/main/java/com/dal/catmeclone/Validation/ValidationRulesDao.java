package com.dal.catmeclone.Validation;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import java.util.List;

public interface ValidationRulesDao {
    public List<String> getRulesFromConfig() throws UserDefinedException;

    public String getRulesValueFromConfig(String ruleName) throws UserDefinedException;
}
