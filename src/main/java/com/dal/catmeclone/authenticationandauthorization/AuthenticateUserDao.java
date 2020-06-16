package com.dal.catmeclone.authenticationandauthorization;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.*;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class AuthenticateUserDao implements Interface_AuthenticateUserDao{

	private CallableStatement stored_pro;
	private Connection connection;

	DataBaseConnection db_connect;


	@Override
	public User authenticateUser(User user)throws SQLException, UserDefinedSQLException {
		User u = null;
		Properties property = SystemConfig.instance().getProperties();
		String authenticateUser = property.getProperty("procedure.authenticateUser");
		try {
			db_connect = SystemConfig.instance().getDatabaseConnection();
			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call " + authenticateUser + "}");

			stored_pro.setString(1,user.getBannerId());
			ResultSet result = stored_pro.executeQuery();
			while(result.next()) {
				u = new User();
				u.setBannerId(result.getString("bannerId"));
				u.setPassword(result.getString("password"));
				u.setUserRoles(new Role(result.getInt("roleid"),result.getString("rolename")));

			}

		}

		catch (SQLException e)
		{

			throw new UserDefinedSQLException("Some error occured");
		}
		
		finally
		{
			db_connect.terminateStatement(stored_pro);
			if(connection!=null)
			db_connect.terminateConnection();
		}
		return u;

	}



	


}

