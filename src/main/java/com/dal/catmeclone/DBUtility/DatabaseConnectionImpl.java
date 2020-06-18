/**
 * 
 */
package com.dal.catmeclone.DBUtility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

/**
 * @author Mayank
 *
 */

public class DatabaseConnectionImpl implements DataBaseConnection{


	private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);

	private String user;
	private String password;
	private String database;
	private String databaseurl;
	private String connectionProperty;
	private String drivername;

	private Connection databaseConnection;
	
	
	public DatabaseConnectionImpl() {
	
		user = System.getenv("spring.datasource.username");
		password = System.getenv("spring.datasource.password");
		database = System.getenv("spring.datasource.name");
		databaseurl = System.getenv("spring.datasource.url");
		connectionProperty = System.getenv("datasource.connection.properties");
		
		//drivername = propertiesUtil.getProperty("spring.datasource.driver-class-name");
	}

	/**
	 * Method to Establish JDBC Connection to Database
	 */
	@Override
	public Connection connect() throws UserDefinedSQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// Throwing user defined exception for incorrect driver
			throw new UserDefinedSQLException(
					"SQL Connection Error : JDBC Driver not supported. Please verify Driver Details");
		}

		try {
			// Setting up the connection
			String databaseConnectionURL = databaseurl + database + "?" + connectionProperty;
			
			databaseConnection = DriverManager.getConnection(databaseConnectionURL, user, password);

		} catch (SQLException e) {
			// Throwing user defined exception for incorrect driver
			throw new UserDefinedSQLException(e.getLocalizedMessage());
		}

		logger.info("Database connected Successfully");
		return databaseConnection;
	}

	/**
	 * Method to Terminate JDBC Connection of Database
	 */
	@Override
	public boolean terminateConnection() {

		try {
			logger.info("Closing Established Connection");
			// Check is database connection is already closed or not
			if (databaseConnection.isClosed() == false) {
				databaseConnection.close();
			}
		} catch (SQLException e) {

			//Logging the error
			logger.error(e.getMessage());

		}
		logger.info("Connection Closed Successfully");
		return true;

	}
	
	@Override
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
