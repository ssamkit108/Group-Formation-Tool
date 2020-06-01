package com.dal.catmeclone.UserSignup;

import java.sql.*;		
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	private CallableStatement statement;
	private Connection connection;
	
	@Value("${procedure.createUser}")
	private String createUserProcedure;

	public boolean createUser(User user) throws SQLException, UserDefinedSQLException
	{	
		connection=DBUtil.connect();
		statement=connection.prepareCall("{call "+createUserProcedure+"}");
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
			if (connection != null)
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
		}
		return true;
	}
	
	
}
