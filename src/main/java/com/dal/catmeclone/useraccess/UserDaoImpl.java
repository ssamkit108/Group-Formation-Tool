package com.dal.catmeclone.useraccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


@Component
public class UserDaoImpl implements UserDao {
	
	final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	@Value("${procedure.finduserBybannerId}")
	private String FindUserByBannerId;
	
	@Value("${procedure.createUser}")
	private String createUserProcedure;
	
	@Value("${procedure.findAllMatchingUser}")
	private String findAllMatchingUser;
	
	private Connection connection;
	

	@Override
	public User findUserByBannerID(String bannerId) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		User user=null;

		try {
			connection = DBUtil.connect();
			CallableStatement statement;
			statement = connection.prepareCall("{call "+FindUserByBannerId+"}");
		
			statement.setString(1, bannerId);
			
			ResultSet rs = statement.executeQuery();
			if(rs!=null) {
				while(rs.next())
				{
					user= new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));				
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return user;
	}

	
	
	@Override
	public boolean createUser(User student) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		try {
			connection = DBUtil.connect();
			CallableStatement statement = connection.prepareCall("{call "+createUserProcedure+"}");
		
			statement.setString(1, student.getBannerId());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getEmail());
			statement.setString(5, student.getPassword());
			
			statement.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("error in creating user");
			return false;
		}finally {
			if (connection != null) {	
				DBUtil.terminateConnection();
		}
	}
	
		return true;
		
	}



	@Override
	public List<User> findAllMatchingUser(String bannerId) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		List<User> listOfUser = new ArrayList<User>();
		try {
				connection = DBUtil.connect();
				CallableStatement statement = connection.prepareCall("{call "+findAllMatchingUser+"}");
				
				statement.setString(1, bannerId);
			
				ResultSet rs = statement.executeQuery();
				if(rs!=null) {
					while(rs.next())
					{
						User user= new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));	
						listOfUser.add(user);
					}
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("error in creating user");
				
			}finally {
				if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
	
		return listOfUser;
	}

}
