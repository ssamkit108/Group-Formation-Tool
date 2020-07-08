package com.dal.catmeclone.UserProfile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl implements UserDao {

	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	EncryptAbstractFactory encryptAbstractFactory=abstractFactory.createEncryptAbstractFactory();
	DBUtilityAbstractFactory dbUtilityAbstractFactory=abstractFactory.createDBUtilityAbstractFactory();

	private DataBaseConnection DBUtil;
	private BCryptPasswordEncryption passwordencoder;
	private CallableStatement statement;
	private Connection connection;
	final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionImpl.class);

	@Override
	public boolean createUser(User student) throws UserDefinedSQLException, DuplicateEntityException {
		try {
			// Establishing Database connection
			DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
			passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();

			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.createUser") + "}");

			statement.setString(1, student.getBannerId());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getEmail());
			statement.setString(5, passwordencoder.encryptPassword(student.getPassword()));

			// Calling Store procedure for execution
			LOGGER.info(
					"Calling Store procedure to execute query and create user with banner id:" + student.getBannerId());
			statement.execute();
			LOGGER.info("User:" + student.getBannerId() + " Inserted in the database successfully.");

		} catch (SQLIntegrityConstraintViolationException e) {

			LOGGER.error("Duplicate entry for email found. Error Encountered while creating user by bannerid: "
					+ student.getBannerId());
			LOGGER.error(e.getLocalizedMessage());
			throw new DuplicateEntityException("User with this details already exist in our system,");

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			LOGGER.error("Error Encountered while creating user by bannerid: " + student.getBannerId());
			LOGGER.error(e.getLocalizedMessage());
			throw new UserDefinedSQLException(
					"User with BannerID" + student.getBannerId() + " already Exist in our system.");
		} finally {

			// Terminating the connection
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}
		LOGGER.info("User with banner id:" + student.getBannerId() + "created successfully");
		return true;
	}

	@Override
	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException {
		User user = null;
		try {
			// Establishing Database connection
			DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement;
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.finduserBybannerId") + "}");
			statement.setString(1, bannerId);

			// Calling Store procedure for execution
			LOGGER.info("Calling Store procedure to execute query and get result");
			ResultSet rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				}
			}

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			LOGGER.error("Error Encountered while finding user by bannerid: " + bannerId);
			LOGGER.error(e.getLocalizedMessage());
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
			DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			CallableStatement statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.findAllMatchingUser") + "}");

			statement.setString(1, bannerId);

			// Calling Store procedure for execution
			LOGGER.info("Calling Store procedure to execute query and get result");
			ResultSet rs = statement.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
					listOfUser.add(user);
				}
			}

		} catch (SQLException e) {
			// Handling error encountered and throwing custom user exception
			LOGGER.error("Error Encountered while finding user by bannerid: " + bannerId);
			LOGGER.error(e.getLocalizedMessage());

		} finally {
			// Terminating the connection
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}
		return listOfUser;
	}

	@Override
	public List<User> getAllUsers() throws UserDefinedSQLException {
		List<User> c;
		ResultSet rs;
		c = new ArrayList<User>();
		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = DBUtil.connect();
		try {
			CallableStatement statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.GetAllUsers") + "}");
			rs = statement.executeQuery();
			while (rs.next()) {
				c.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			LOGGER.info("Retrieved successfully from the database");
		} catch (Exception e) {
			LOGGER.error("Unable to execute query to get all courses");
		} finally {
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return c;
	}
}
