package com.dal.catmeclone.authenticationandauthorization;

import java.sql.*;
import java.util.Properties;
import org.springframework.stereotype.Component;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.*;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class AuthenticateUserDaoImpl implements AuthenticateUserDao {

	private CallableStatement statement;
	private Connection connection;
	DataBaseConnection db;

	@Override
	public User authenticateUser(User currentUser) throws SQLException, UserDefinedSQLException {
		User user = null;
		Properties property = SystemConfig.instance().getProperties();
		String authenticateUser = property.getProperty("procedure.authenticateUser");
		try {
			db = SystemConfig.instance().getDatabaseConnection();
			connection = db.connect();
			statement = connection.prepareCall("{call " + authenticateUser + "}");

			statement.setString(1, currentUser.getBannerId());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				user = new User();
				user.setBannerId(result.getString("bannerId"));
				user.setPassword(result.getString("password"));
				user.setUserRoles(new Role(result.getInt("roleid"), result.getString("rolename")));
			}
		} catch (SQLException e) {
			throw new UserDefinedSQLException("Some error occured");
		} finally {
			db.terminateStatement(statement);
			if (connection != null)
				db.terminateConnection();
		}
		return user;
	}
}