package com.dal.catmeclone.DBUtility;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

import java.sql.CallableStatement;
import java.sql.Connection;

public interface DataBaseConnection {

    /**
     * Method to Establish JDBC Connection to Database
     */
    public Connection connect() throws UserDefinedSQLException;

    /**
     * Method to Terminate JDBC Connection of Database
     */
    public boolean terminateConnection();

    /**
     * Method to Terminate JDBC Statement of Database
     */
    public void terminateStatement(CallableStatement statement) throws UserDefinedSQLException;
}
