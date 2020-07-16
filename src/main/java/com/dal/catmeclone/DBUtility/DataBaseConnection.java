package com.dal.catmeclone.DBUtility;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;

import java.sql.CallableStatement;
import java.sql.Connection;

public interface DataBaseConnection {

    public Connection connect() throws UserDefinedException;

    public boolean terminateConnection() throws UserDefinedException;

    public void terminateStatement(CallableStatement statement) throws UserDefinedException;
}
