package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {

    private final Logger LOGGER = Logger.getLogger(SignupController.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    private DataBaseConnection DBUtil;
    private BCryptPasswordEncryption passwordencoder;
    private CallableStatement statement;
    private Connection connection;

    @Override
    public boolean createUser(User user) throws UserDefinedSQLException, DuplicateEntityException {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();

            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            CallableStatement statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.createUser") + "}");

            statement.setString(1, user.getBannerId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, passwordencoder.encryptPassword(user.getPassword()));

            LOGGER.info(
                    "Calling Store procedure to execute query and create user with banner id:" + user.getBannerId());
            statement.execute();
            LOGGER.info("User:" + user.getBannerId() + " Inserted in the database successfully.");

        } catch (SQLIntegrityConstraintViolationException e) {

            LOGGER.warning("Duplicate entry for email found. Error Encountered while creating user by bannerid: "
                    + user.getBannerId());
            LOGGER.warning(e.getLocalizedMessage());
            throw new DuplicateEntityException("User with this details already exist in our system,");

        } catch (SQLException e) {
            LOGGER.warning("Error Encountered while creating user by bannerid: " + user.getBannerId());
            LOGGER.warning(e.getLocalizedMessage());
            throw new UserDefinedSQLException(
                    "User with BannerID" + user.getBannerId() + " already Exist in our system.");
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        LOGGER.info("User with banner id:" + user.getBannerId() + "created successfully");
        return true;
    }

    @Override
    public User findUserByBannerID(String bannerId) throws UserDefinedSQLException {
        User user = null;
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            CallableStatement statement;
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.finduserBybannerId") + "}");
            statement.setString(1, bannerId);

            LOGGER.info("Calling Store procedure to execute query and get result");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                }
            }

        } catch (SQLException e) {
            LOGGER.warning("Error Encountered while finding user by bannerid: " + bannerId);
            LOGGER.warning(e.getLocalizedMessage());
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } finally {
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
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            CallableStatement statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.findAllMatchingUser") + "}");

            statement.setString(1, bannerId);
            LOGGER.info("Calling Store procedure to execute query and get result");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                    listOfUser.add(user);
                }
            }

        } catch (SQLException e) {
            LOGGER.warning("Error Encountered while finding user by bannerid: " + bannerId);
            LOGGER.warning(e.getLocalizedMessage());
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return listOfUser;
    }

    @Override
    public List<User> getAllUsers() throws UserDefinedSQLException, Exception {
        List<User> userList;
        ResultSet resultSet;
        userList = new ArrayList<User>();
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        try {
            CallableStatement statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.GetAllUsers") + "}");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
            }
            LOGGER.info("Retrieved successfully from the database");
        } catch (SQLException e) {
            LOGGER.warning("SQL error generated while getting all the users.");
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Unable to execute query to get all courses");
            throw new Exception(e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return userList;
    }
}
