package com.dal.catmeclone.Validation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public class ValidationRulesDaoImpl implements ValidationRulesDao {

	final Logger LOGGER = LoggerFactory.getLogger(ValidationRulesDaoImpl.class);
	private DataBaseConnection DBUtil;
	private CallableStatement statement;
	private Connection connection;
	ResultSet rs;

	private List<String> rules;

	private void loadRulesFromDB() {
		try {
			rules = new ArrayList<String>();
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetchPasswordRules") + "}");
			rs = statement.executeQuery();

			while (rs.next()) {
				rules.add(rs.getString("Policy_Name"));
			}
		} catch (UserDefinedSQLException e) {
			LOGGER.error("Error in loading sign up validation rules. ", e);
		} catch (SQLException e) {
			LOGGER.error("Error in loading sign up validation rules. ", e);
		} finally {
			try {
				DBUtil.terminateStatement(statement);
				if (connection != null) {
					DBUtil.terminateConnection();
				}
			} catch (UserDefinedSQLException e) {
				LOGGER.error("Error in loading sign up validation rules. ", e);
			}

		}

	}

	@Override
	public List<String> getRulesFromConfig() {
		loadRulesFromDB();
		return rules;
	}

	@Override
	public String getRulesValueFromConfig(String ruleName) {
		String ruleValue = "";
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetchRuleValue") + "}");
			statement.setString(1, ruleName);
			rs = statement.executeQuery();

			while (rs.next()) {
				ruleValue = rs.getString("Policy_Value");
			}
		} catch (UserDefinedSQLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtil.terminateStatement(statement);
				if (connection != null) {
					DBUtil.terminateConnection();
				}
			} catch (UserDefinedSQLException e) {
				LOGGER.error("Error in loading sign up validation rules. ", e);
			}

		}
		return ruleValue;
	}

}
