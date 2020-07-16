package com.dal.catmeclone.DBUtility;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;

import java.sql.CallableStatement;
import java.sql.Connection;

public interface DataBaseConnection {

    /**
     * Method to Establish JDBC Connection to Database
     */
    public Connection connect() throws UserDefinedException;

    /**
     * Method to Terminate JDBC Connection of Database
     */
    public boolean terminateConnection() throws UserDefinedException;

    /**
     * Method to Terminate JDBC Statement of Database
     */
    public void terminateStatement(CallableStatement statement) throws UserDefinedException;
}
