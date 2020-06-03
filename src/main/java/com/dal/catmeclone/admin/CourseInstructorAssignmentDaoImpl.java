package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentDaoImpl implements CourseInstructorAssignmentDao {

	private Connection con;
	private CallableStatement statement;
	DatabaseConnection db = new DatabaseConnection();
	ResultSet rs;
	List<User> c;
	
	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		con = db.connect();
		try {
			statement = con.prepareCall("{call enrollmentincourse(?,?,?)}");
			statement.setString(1, Instructor.getBannerId());
			statement.setInt(2, course.getCourseID());
			statement.setString(3, role.getRoleName());
			statement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	@Override
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException{
		c = new ArrayList<User>();
		con = db.connect();
		try {
		statement = con.prepareCall("{CALL GetAllUsers()}");
		rs = statement.executeQuery();
		while(rs.next()) {
			c.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5)));
		}
		}
		catch(Exception e) {
			System.out.println("Unable to execute query");
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
		return c;
	}

}
