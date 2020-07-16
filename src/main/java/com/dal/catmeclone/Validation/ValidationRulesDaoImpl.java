package com.dal.catmeclone.Validation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class ValidationRulesDaoImpl implements ValidationRulesDao {

    final Logger LOGGER = Logger.getLogger(ValidationRulesDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    ResultSet resultSet;
    private DataBaseConnection DBUtil;
    private CallableStatement statement;
    private Connection connection;
    private List<String> rules;

    @Override
    public List<String> getRulesFromConfig() throws UserDefinedSQLException, SQLException, Exception {
        loadRulesFromDB();
        return rules;
    }

    private void loadRulesFromDB() throws UserDefinedSQLException, SQLException, Exception {
        try {
            rules = new ArrayList<String>();
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetchPasswordRules") + "}");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rules.add(resultSet.getString("Policy_Name"));
            }
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("Error in loading sign up validation rules. ");
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } catch (SQLException e) {
            LOGGER.warning("Error in loading sign up validation rules. ");
            throw new SQLException(e.getLocalizedMessage());
        } finally {
            try {
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (UserDefinedSQLException e) {
                LOGGER.warning("Error in loading sign up validation rules. ");
            }
        }
    }

    @Override
    public String getRulesValueFromConfig(String ruleName) throws UserDefinedSQLException, SQLException, Exception {
        String ruleValue = "";
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetchRuleValue") + "}");
            statement.setString(1, ruleName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ruleValue = resultSet.getString("Policy_Value");
            }
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("Error in getting rules values. ");
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } catch (SQLException e) {
            LOGGER.warning("Error in getting rules values.  ");
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return ruleValue;
    }

}
