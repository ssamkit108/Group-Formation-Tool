package com.dal.catmeclone.authenticationandauthorization;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.*;
import com.dal.catmeclone.exceptionhandler.*;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.model.UserDetails;

public class AuthenticateUserDao implements Interface_AuthenticateUserDao{

	private CallableStatement stored_pro;
	private Connection connection;



	@Override
	public User getbyBannerId(User user)throws SQLException, UserDefinedSQLException {
		User u = null;

		try {
			DatabaseConnection db_connect = new DatabaseConnection();


			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call getbybannerId(?)}");

			stored_pro.setString(1,user.getBannerId());
			ResultSet result = stored_pro.executeQuery();
			while(result.next()) {
				u = new User();
				u.setBannerId(result.getString("bannerId"));
				u.setPassword(result.getString("password"));

			}

		}

		catch (SQLException e)
		{
			return null;
		}
		finally
		{
			if (stored_pro != null)
			{
				stored_pro.close();
			}
			if (connection != null)
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
		}
		return u;

	}



	@Override
	public ArrayList<Course> getallcoursesbyuser(User user) throws SQLException, UserDefinedSQLException {
		ArrayList<Course> crclst = new ArrayList<Course>();

		try {
			DatabaseConnection db_connect = new DatabaseConnection();

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
			return null;
		}
		finally
		{
			if (stored_pro != null)
			{
				stored_pro.close();
			}
			if (connection != null)
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
		}
		return crclst;




	}



	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		ArrayList<Course> allcrclst = new ArrayList<Course>();

		try {
			DatabaseConnection db_connect = new DatabaseConnection();

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
			if (stored_pro != null)
			{
				stored_pro.close();
			}
			if (connection != null)
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
		}
		return allcrclst;

	}



	@Override
	public ArrayList<UserDetails> getrolebyuser(User user) throws UserDefinedSQLException, SQLException {
		ArrayList<UserDetails> role_data = new ArrayList<UserDetails>();

		try {
			DatabaseConnection db_connect = new DatabaseConnection();
			connection = db_connect.connect();
			stored_pro = connection.prepareCall("{call getrolebyuser(?)}");
			stored_pro.setString(1,user.getBannerId());

			ResultSet result = stored_pro.executeQuery();
			UserDetails ud= null;
			while(result.next()) {
				ud = new UserDetails();
				System.out.println(result.getString("bannerid"));
				ud.setBannerId(result.getString("bannerid"));
				ud.setCourseId(result.getInt("courseid"));
				ud.setCourseName(result.getString("coursename"));
				ud.setRole_tagged(result.getString("rolename"));
				role_data.add(ud);
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		finally
		{
			if (stored_pro != null)
			{
				stored_pro.close();
			}
			if (connection != null)
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
		}
		return role_data;
	}
}






