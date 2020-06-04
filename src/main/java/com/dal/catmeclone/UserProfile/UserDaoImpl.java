package com.dal.catmeclone.UserProfile;

import java.sql.*;		
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	private CallableStatement statement;
	private Connection connection;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

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
			logger.info("User:"+user.getBannerId()+" Inserted in the database successfully.");
		}catch ( DataIntegrityViolationException e) {
			logger.error("User"+user.getBannerId()+" already exist in the system");
			return false;
		}
		catch (SQLException e)
		{
			logger.error("Some SQL error generated in the UserDao.");
			return false;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
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
