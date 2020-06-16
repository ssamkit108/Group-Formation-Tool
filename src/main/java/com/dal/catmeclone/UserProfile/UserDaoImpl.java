package com.dal.catmeclone.UserProfile;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dal.catmeclone.model.*;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.DuplicateUserRelatedException;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class UserDaoImpl implements UserDao {
	

	private DataBaseConnection DBUtil;
	
	private CallableStatement statement;
	private Connection connection;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);



	@Override
	public boolean createUser(User student) throws UserDefinedSQLException, DuplicateUserRelatedException {

		try {
			// Establishing Database connection
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement = connection.prepareCall("{call " + properties.getProperty("procedure.createUser") + "}");

			statement.setString(1, student.getBannerId());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getEmail());
			statement.setString(5, student.getPassword());

			// Calling Store procedure for execution
			logger.info(
					"Calling Store procedure to execute query and create user with banner id:" + student.getBannerId());
			statement.execute();
			logger.info("User:"+student.getBannerId()+" Inserted in the database successfully.");

		} catch (SQLIntegrityConstraintViolationException e) {
			
			logger.error("Duplicate entry for email found. Error Encountered while creating user by bannerid: "
					+ student.getBannerId());
			logger.error(e.getLocalizedMessage());
			throw new DuplicateUserRelatedException(e.getLocalizedMessage());

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			logger.error("Error Encountered while creating user by bannerid: " + student.getBannerId());
			logger.error(e.getLocalizedMessage());
			return false;
		} finally {

			// Terminating the connection
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}

		logger.info("User with banner id:" + student.getBannerId() + "created successfully");
		return true;

	}
	
	

	@Override
	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException {

		User user = null;
		try {
			// Establishing Database connection
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement;

			statement = connection.prepareCall("{call " + properties.getProperty("procedure.finduserBybannerId") + "}");
			statement.setString(1, bannerId);

			// Calling Store procedure for execution
			logger.info("Calling Store procedure to execute query and get result");
			ResultSet rs = statement.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				}
			}

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			logger.error("Error Encountered while finding user by bannerid: " + bannerId);
			logger.error(e.getLocalizedMessage());
			throw new UserDefinedSQLException(e.getLocalizedMessage());
		} finally {
			// Terminating the connection
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}
		return user;
	}
	
	@Override
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException {
		
		List<User> listOfUser = new ArrayList<User>();
		try {
			// Establishing Database connection
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement = connection.prepareCall("{call " + properties.getProperty("procedure.findAllMatchingUser") + "}");

			statement.setString(1, bannerId);

			// Calling Store procedure for execution
			logger.info("Calling Store procedure to execute query and get result");
			ResultSet rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					listOfUser.add(user);
				}
			}

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			logger.error("Error Encountered while finding user by bannerid: " + bannerId);
			logger.error(e.getLocalizedMessage());

		} finally {
			// Terminating the connection
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}

		return listOfUser;
	}

	

}
