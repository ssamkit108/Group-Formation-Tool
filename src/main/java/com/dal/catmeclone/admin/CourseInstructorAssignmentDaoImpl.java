package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class CourseInstructorAssignmentDaoImpl implements CourseInstructorAssignmentDao {

	@Autowired
	DatabaseConnection db;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	private Connection con;
	private CallableStatement statement;
	
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
			logger.info("Course:"+Instructor.getBannerId()+course.getCourseID()+role.getRoleName()+"Instructor Enrolled successfully");
			
		} catch (SQLException e) {
			logger.error("Unable to execute query to check if course exists");
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
		List<User> c;
		ResultSet rs;
		c = new ArrayList<User>();
		con = db.connect();
		try {
		statement = con.prepareCall("{CALL GetAllUsers()}");
		rs = statement.executeQuery();
		while(rs.next()) {
			c.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5)));
		}
		logger.info("Retrieved successfully from the database");
		}
		catch(Exception e) {
			logger.error("Unable to execute query to get all courses");
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
