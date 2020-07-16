package com.dal.catmeclone.Validation;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

import java.sql.SQLException;
import java.util.List;

public interface ValidationRulesDao {
    public List<String> getRulesFromConfig() throws UserDefinedSQLException, SQLException, Exception;

    public String getRulesValueFromConfig(String ruleName) throws UserDefinedSQLException, SQLException, Exception;
}
