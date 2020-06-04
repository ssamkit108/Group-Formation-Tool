/**
 * 
 */
package com.dal.catmeclone.DBUtility;

import java.sql.CallableStatement;
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
			//Logging the error
			logger.error(e.getMessage());
		}
		return true;

	}
	
	public void terminateStatement(CallableStatement statement) throws UserDefinedSQLException
    {
          if (statement != null)
          {
              try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                logger.error("Error Occured in Closing Statement");
                throw new UserDefinedSQLException("Some Error Occured");
            }
          }
    }

}
