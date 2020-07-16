package com.dal.catmeclone.DBUtility;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnectionImpl implements DataBaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionImpl.class.getName());
    private String user;
    private String password;
    private String database;
    private String databaseurl;
    private String connectionProperty;

    private Connection databaseConnection;

    public DatabaseConnectionImpl() {
        user = System.getenv("spring.datasource.username");
        password = System.getenv("spring.datasource.password");
        database = System.getenv("spring.datasource.name");
        databaseurl = System.getenv("spring.datasource.url");
        connectionProperty = System.getenv("datasource.connection.properties");
    }


    @Override
    public Connection connect() throws UserDefinedException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	LOGGER.severe("Resource Connection Error- Incorrect Driver details");
            throw new UserDefinedException("Resource Connection Error : Please try again later");
        }

        try {
            String databaseConnectionURL = databaseurl + database + "?" + connectionProperty;
            databaseConnection = DriverManager.getConnection(databaseConnectionURL, user, password);
        } catch (SQLException e) {
        	LOGGER.severe("DATABASE SERVER ERROR. occured while setting up the database."+e.getLocalizedMessage() );
        	throw new UserDefinedException("DATABASE SERVER ERROR. Resource connectivity error. Please try again later");
        }
        return databaseConnection;
    }

    @Override
    public boolean terminateConnection() throws UserDefinedException{
        try {
            if (databaseConnection.isClosed() == false) {
                databaseConnection.close();
            }
        } catch (SQLException e) {
        	LOGGER.severe("DATABASE SERVER ERROR .occured while terminating the connection. "+e.getMessage() );
        	throw new UserDefinedException("DATABASE SERVER ERROR .Resource connectivity error. Please try again later");
        }
        return true;
    }

    @Override
    public void terminateStatement(CallableStatement statement) throws UserDefinedException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.severe("DATABASE SERVER ERROR .Occured in Closing Statement "+e.getMessage());
                throw new UserDefinedException("DATABASE SERVER ERROR");
            }
        }
    }
}
