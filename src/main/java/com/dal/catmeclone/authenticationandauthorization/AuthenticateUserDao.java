package com.dal.catmeclone.authenticationandauthorization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.*;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class AuthenticateUserDao implements Interface_AuthenticateUserDao{

	private CallableStatement stored_pro;
	private Connection connection;

	@Autowired
	DatabaseConnection db_connect;
	
	@Value("${procedure.authenticateUser}")
	private String authenticateUser;

	@Override
	public User authenticateUser(User user)throws SQLException, UserDefinedSQLException {
		User u = null;

		try {
			//DatabaseConnection db_connect = new DatabaseConnection();


			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call " + authenticateUser + "}");

			stored_pro.setString(1,user.getBannerId());
			ResultSet result = stored_pro.executeQuery();
			while(result.next()) {
				u = new User();
				u.setBannerId(result.getString("bannerId"));
				u.setPassword(result.getString("password"));
				u.setUserRoles(new Role(result.getInt("roleid"),result.getString("rolename")));

			}

		}

		catch (SQLException e)
		{	
			return null;
		}
		finally
		{
			db_connect.terminateConnection();
		}
		return u;

	}



	@Override
	public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedSQLException {
		ArrayList<Course> crclst = new ArrayList<Course>();

		try {
			//DatabaseConnection db_connect = new DatabaseConnection();

			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call GetCoursesForUser(?)}");
			stored_pro.setString(1,user.getBannerId());


			stored_pro.setString(1,user.getBannerId());
			ResultSet result = stored_pro.executeQuery();

			Course c = null;
			while(result.next()) {
				c = new Course();
				c.setCourseID(result.getInt("courseid"));
				c.setCourseName(result.getString("coursename"));
				crclst.add(c);
			}
		}

		catch (SQLException e)
		{
			throw new UserDefinedSQLException("Some error occured");
		}
		finally
		{
			db_connect.terminateConnection();
		}
		return crclst;




	}



	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		ArrayList<Course> allcrclst = new ArrayList<Course>();

		try {
			//DatabaseConnection db_connect = new DatabaseConnection();

			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call GetAllCourses()}");

			ResultSet result = stored_pro.executeQuery();
			Course c = null;
			while(result.next()) {
				c = new Course();
				c.setCourseID(result.getInt("courseid"));
				c.setCourseName(result.getString("coursename"));
				allcrclst.add(c);
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		finally
		{
			db_connect.terminateConnection();
		}
		return allcrclst;

	}



	
}






