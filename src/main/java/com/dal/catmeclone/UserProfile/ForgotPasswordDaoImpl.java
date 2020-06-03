	package com.dal.catmeclone.UserProfile;
	
	import java.sql.CallableStatement;		
	import java.sql.Connection;
	import java.sql.ResultSet;
	import java.sql.SQLException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Component;
	
	import com.dal.catmeclone.DBUtility.DatabaseConnection;
	import com.dal.catmeclone.encrypt.BCryptPasswordEncryption;
	import com.dal.catmeclone.notification.NotificationService;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	
	@Component
	public class ForgotPasswordDaoImpl implements ForgotPasswordDao{
	
		@Autowired
		private DatabaseConnection DBUtil;
		
		final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

		private CallableStatement statement;
		private Connection connection;
		private String sendto;

		
		@Autowired
		NotificationService notificationService;	
		
		@Autowired
		private BCryptPasswordEncryption passwordencoder;
		
		@Value("${from.email}")
		private String fromgmail;
		
		@Value("${from.password}")
		private String fromPassword;
		
		@Value("${procedure.finduserBybannerId}")
		private String FindUserByBannerId;

		@Value("${procedure.UpdatePassword}")
		private String UpdatePassword;
		
		

		public boolean checkexist(String bannerid) {
			try {
				connection = DBUtil.connect();
				statement = connection.prepareCall("{call "+FindUserByBannerId+"}");

				statement.setString(1, bannerid);
				ResultSet rs = statement.executeQuery();
				
				if(rs.next()) {
					sendto=rs.getString("email");
					return true;
				}
				else {
					return false;
				}	
		}catch(Exception e) {
			logger.error("There is error about closing connection in the forgot password Dao.");
			e.printStackTrace();
			return false;
		}
		finally 
		{
			try {
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					if (!connection.isClosed())
					{
						connection.close();
					}
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
		}
		
		@Override
		public void UpdatePassword(String BannerId, String password) {
			try {
			connection = DBUtil.connect();
			statement = connection.prepareCall("{call "+UpdatePassword+"}");
			statement.setString(1,BannerId);
			statement.setString(2, passwordencoder.encryptPassword(password));
			statement.execute();
	        String body ="Hi"+
					"\n\n" +
					"Here is a new password for your account "+
					"\nPlease find below your login credential: "+
					"\n\n\n"+
					"\nUsername: "+BannerId+
					"\nPassword: "+password+
					"\n\nYou are most welcomed to be a part of this organisation."+
					"\n\nBest Regards,\nCSCI5708-Grp12";
	        body += "\nNote: for security reason, "
	                + "you must change your password after logging in.";


			String subject = "Forgot password";
			

			notificationService.send(fromgmail,fromPassword,sendto,subject,body);
			logger.info("The forgot password mail sent successfully");
			
			}
			catch(SQLException e) {
				logger.error("There is SQL error in the forgot password Dao.");
			}
			catch(Exception e) {
				logger.error(e.getMessage());
					}finally {
				try {
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					if (!connection.isClosed())
					{
						connection.close();
					}
				}
				}catch(Exception e) {
					logger.error("There is error about closing connection in the forgot password Dao.");
					logger.error(e.getMessage());
				}
			}
			
		}
	
	}
