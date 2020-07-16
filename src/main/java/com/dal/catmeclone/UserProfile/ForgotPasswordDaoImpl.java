package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notification.NotificationService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ForgotPasswordDaoImpl implements ForgotPasswordDao {

    final Logger LOGGER = Logger.getLogger(ForgotPasswordDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    EncryptAbstractFactory encryptAbstractFactory = abstractFactory.createEncryptAbstractFactory();
    NotificationAbstractFactory notificationAbstractFactory = abstractFactory.createNotificationAbstractFactory();
    NotificationService notificationService;
    BCryptPasswordEncryption passwordencoder;
    private DataBaseConnection DBUtil;
    private CallableStatement statement;
    private Connection connection;
    private String sendto;

    public boolean checkExist(String bannerid) throws SQLException, Exception {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.finduserBybannerId") + "}");

            statement.setString(1, bannerid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                sendto = resultSet.getString("email");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.warning("SQL error occurred while checking user exist or not.");
            throw new SQLException(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
    }

    @Override
    public void updateToken(String BannerId, String token) throws SQLException, Exception {
        try {
            notificationService = notificationAbstractFactory.createNotificationService();
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.Updatetoken") + " }");
            statement.setString(1, token);
            statement.setString(2, BannerId);
            statement.execute();
            String appurl;
            appurl = System.getenv("appurl") + "/reset?token=" + token;
            notificationService.sendNotificationForPassword(BannerId, appurl, sendto);
            LOGGER.info("The forgot password mail sent successfully");
        } catch (SQLException e) {
            LOGGER.warning("There is SQL error in the forgot password Dao." + e.getLocalizedMessage());
            throw new SQLException("There is SQL error in the forgot password Dao.");
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }

        }
    }

    @Override
    public void setNewPassword(String BannerId, String password) throws SQLException, Exception {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            passwordencoder = encryptAbstractFactory.createBCryptPasswordEncryption();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.UpdatePassword") + "}");
            statement.setString(1, BannerId);
            statement.setString(2, passwordencoder.encryptPassword(password));
            statement.execute();
        } catch (SQLException e) {
            LOGGER.warning("There is SQL error in the forgot password Dao." + e.getLocalizedMessage());
            throw new SQLException("Sorry..Password could not able to reset. Try after sometime.");
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
    }

    public String checkTokenExist(String token) throws SQLException, Exception {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.findByResetToken") + "}");
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            String BannerID;
            if (resultSet.next()) {
                BannerID = resultSet.getString("bannerid");
            } else {
                BannerID = null;
            }
            return BannerID;
        } catch (SQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
            throw new SQLException(e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
    }
}
