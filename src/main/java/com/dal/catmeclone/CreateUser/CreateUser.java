package com.dal.catmeclone.CreateUser;

import java.sql.*;	
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

public class CreateUser implements Interface_CreateUser {
	
	DatabaseConnection db_con=new DatabaseConnection();
	private CallableStatement statement;
	private Connection con;
	
	public boolean createUser(User user) throws SQLException, UserDefinedSQLException
	{	
		con=db_con.connect();
		statement=con.prepareCall("{call CreateUser(?, ?, ?, ?, ?)}");
		try
		{
			statement.setString(1, user.getBannerId());
			statement.setString(2,user.getFirstName());
			statement.setString(3,user.getLastName());
			statement.setString(4,user.getEmail());
			statement.setString(5,user.getPassword());
			statement.execute();
		}
		catch (SQLException e)
		{
			return false;
		}
		finally
		{
			if (statement != null)
			{
				statement.close();
			}
			if (con != null)
			{
				if (!con.isClosed())
				{
					con.close();
				}
			}
		}
		return true;
	}
}
