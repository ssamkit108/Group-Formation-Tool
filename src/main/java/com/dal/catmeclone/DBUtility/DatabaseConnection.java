/**
 * 
 */
package com.dal.catmeclone.DBUtility;

import java.sql.Connection;	
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

/**
 * @author Mayank
 *
 */

@Configuration
public class DatabaseConnection {

	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
/*
	@Value("${spring.datasource.username}")
	private String user;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.name}")
	private String database;

	@Value("${spring.datasource.url}")
	private String databaseurl;

	@Value("${datasource.connection.properties}")
	private String connectionProperty;

	@Value("${spring.datasource.driver-class-name}")
	private String drivername;

	private static Connection databaseConnection;
*/
	
	private String user="CSCI5308_12_DEVINT_USER";

	private String password="CSCI5308_12_DEVINT_12313";

	private String database="CSCI5308_12_DEVINT";

	private String databaseurl="jdbc:mysql://db-5308.cs.dal.ca:3306/";

	private String connectionProperty="useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";

	private String drivername="com.mysql.jdbc.Driver";

	private static Connection databaseConnection;
	/**
	 * Method to Establish JDBC Connection to Database
	 */
	public Connection connect() throws UserDefinedSQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			//Throwing user defined exception for incorrect driver
			throw new UserDefinedSQLException(
					"SQL Connection Error : JDBC Driver not supported. Please verify Driver Details");
		}

		try {
			// Setting up the connection
			String databaseConnectionURL = databaseurl + database + "?" + connectionProperty;
			databaseConnection = DriverManager.getConnection(databaseConnectionURL, user, password);
			
		} catch (SQLException e) {
			//Throwing user defined exception for incorrect driver
			throw new UserDefinedSQLException(
					"SQL Connection Error: Please verify the Credentials : \n" + e.getMessage());
		}

		return databaseConnection;
	}

	
	
	/**
	 * Method to Terminate JDBC Connection of Database
	 */
	public boolean terminateConnection() {

		try {
			//Check is database connection is already closed or not
			if (databaseConnection.isClosed() == false) {
				databaseConnection.close();
			}
		} catch (SQLException e) {
			//Logging the erro
			logger.error(e.getMessage());
		}
		return true;

	}

}
