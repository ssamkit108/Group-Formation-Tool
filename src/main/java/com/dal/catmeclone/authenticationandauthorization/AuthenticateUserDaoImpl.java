package com.dal.catmeclone.authenticationandauthorization;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

@Component
public class AuthenticateUserDaoImpl implements AuthenticateUserDao {

	final Logger LOGGER = LoggerFactory.getLogger(AuthenticateUserDaoImpl.class);

	private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	private DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	private DataBaseConnection dbUtility = null;
	private CallableStatement statement = null;
	private Connection connection = null;

	@Override
	public User authenticateUser(User currentUser) throws UserDefinedException {
		User user = null;
		Properties property = SystemConfig.instance().getProperties();
		String authenticateUser = property.getProperty("procedure.authenticateUser");
		try {
			dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
			connection = dbUtility.connect();
			statement = connection.prepareCall("{call " + authenticateUser + "}");

			statement.setString(1, currentUser.getBannerId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				user = new User();
				user.setBannerId(result.getString("bannerId"));
				user.setPassword(result.getString("password"));
				user.setUserRoles(new Role(result.getInt("roleid"), result.getString("rolename")));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new UserDefinedException("Error Encountered while authenticating user. Please try again later");
		}
		 finally {
			dbUtility.terminateStatement(statement);
			if (connection != null)
				dbUtility.terminateConnection();
		}
		return user;
	}
}