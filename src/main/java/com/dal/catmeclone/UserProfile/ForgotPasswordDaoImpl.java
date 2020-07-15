package com.dal.catmeclone.UserProfile;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ForgotPasswordDaoImpl implements ForgotPasswordDao {

    final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordDaoImpl.class);
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

    public boolean checkexist(String bannerid) throws Exception {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.finduserBybannerId") + "}");

            statement.setString(1, bannerid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                sendto = rs.getString("email");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("There is error about closing connection in the forgot password Dao.");
            throw new Exception("There is error about closing connection in the forgot password Dao.");
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
    }

    @Override
    public void UpdateToken(String BannerId, String token) throws Exception {
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
            LOGGER.error("There is SQL error in the forgot password Dao." + e.getLocalizedMessage());
            throw new Exception("There is SQL error in the forgot password Dao.");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            try {
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (Exception e) {
                LOGGER.error("There is error about closing connection in the forgot password Dao.");
                LOGGER.error(e.getLocalizedMessage());
                throw new Exception(e.getLocalizedMessage());
            }
        }

    }

    @Override
    public void SetNewPassword(String BannerId, String password) throws Exception {
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
            LOGGER.error("There is SQL error in the forgot password Dao." + e.getLocalizedMessage());
            throw new Exception("Sorry..Password could not able to reset. Try after sometime.");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            try {
                // Terminating the connection
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (Exception e) {
                LOGGER.error("There is error about closing connection in the forgot password Dao.");
                LOGGER.error(e.getLocalizedMessage());
                throw new Exception(e.getLocalizedMessage());
            }
        }

    }

    // This method will check that token is already in the database or not
    public String checktokenexist(String token) throws Exception {
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.findByResetToken") + "}");
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();
            String BannerID;
            if (rs.next()) {
                BannerID = rs.getString("bannerid");
            } else {
                BannerID = "";
            }
            return BannerID;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                // Terminating the connection
                DBUtil.terminateStatement(statement);
                if (connection != null) {
                    DBUtil.terminateConnection();
                }
            } catch (Exception e) {
                LOGGER.error("There is error about closing connection in the forgot password Dao.");
                LOGGER.error(e.getLocalizedMessage());
                throw new Exception(e.getLocalizedMessage());
            }
        }
    }
}
