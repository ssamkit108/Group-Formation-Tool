package com.dal.catmeclone.UserProfile;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgotPasswordDaoImpl implements ForgotPasswordDao {

	private DataBaseConnection DBUtil ;

	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);

	private CallableStatement statement;
	private Connection connection;
	private String sendto;

	//Getting instance of NotificationService
	NotificationService notificationService ;

	//Getting instance of BCryptPasswordEncryption
	private BCryptPasswordEncryption passwordencoder;


	

	public boolean checkexist(String bannerid) throws Exception {
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
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
			logger.error("There is error about closing connection in the forgot password Dao.");
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
			notificationService=SystemConfig.instance().getNotificationService();
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			statement=connection.prepareCall("{call "+properties.getProperty("procedure.Updatetoken")+" }");
			statement.setString(1, token);
			statement.setString(2, BannerId);
			statement.execute();
			String appurl;
			appurl="http://localhost:8080/"+"reset?token="+token;
			notificationService.sendNotificationForPassword(BannerId, appurl, sendto);
			logger.info("The forgot password mail sent successfully");

		} catch (SQLException e) {
            logger.error("There is SQL error in the forgot password Dao."+e.getLocalizedMessage());
            throw new Exception("There is SQL error in the forgot password Dao.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
		} finally {
			try {
				// Terminating the connection
				DBUtil.terminateStatement(statement);
				if (connection != null) {
					DBUtil.terminateConnection();
				}
			} catch (Exception e) {
				logger.error("There is error about closing connection in the forgot password Dao.");
				logger.error(e.getLocalizedMessage());
				throw new Exception(e.getLocalizedMessage());
			}
		}

	}
	@Override
	public void SetNewPassword(String BannerId, String password) throws Exception {	
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			passwordencoder=SystemConfig.instance().getBcryptPasswordEncrption();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.UpdatePassword") + "}");
			statement.setString(1, BannerId);
			statement.setString(2, passwordencoder.encryptPassword(password));
			statement.execute();
		} catch (SQLException e) {
            logger.error("There is SQL error in the forgot password Dao."+e.getLocalizedMessage());
            throw new Exception("Sorry..Password could not able to reset. Try after sometime.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
		} finally {
			try {
				// Terminating the connection
				DBUtil.terminateStatement(statement);
				if (connection != null) {
					DBUtil.terminateConnection();
				}
			} catch (Exception e) {
				logger.error("There is error about closing connection in the forgot password Dao.");
				logger.error(e.getLocalizedMessage());
				throw new Exception(e.getLocalizedMessage());
			}
		}

	}
	
	//This method will check that token is already in the database or not
	public String checktokenexist(String token) throws Exception {
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.findByResetToken") + "}");
			statement.setString(1, token);
			ResultSet rs = statement.executeQuery();
			String BannerID;
			if(rs.next()) {
				BannerID=rs.getString("bannerid");
			}
			else {
				BannerID="";
			}
			return BannerID;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				// Terminating the connection
				DBUtil.terminateStatement(statement);
				if (connection != null) {
					DBUtil.terminateConnection();
				}
			} catch (Exception e) {
				logger.error("There is error about closing connection in the forgot password Dao.");
				logger.error(e.getLocalizedMessage());
				throw new Exception(e.getLocalizedMessage());
			}
		}
	}
}
