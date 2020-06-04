package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

@Component
public class CourseManagementDaoImpl implements CourseManagementDao{

	@Autowired
	DatabaseConnection db;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
	
	private CallableStatement statement;
	private Connection con;
	private ResultSet rs;
	
	List<Course> c;

	@Override
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException{
		c = new ArrayList<Course>();
		con = db.connect();
		try {
		statement = con.prepareCall("{CALL GetAllCourses()}");
		rs = statement.executeQuery();
		while(rs.next()) {
			c.add(new Course(rs.getInt(1),rs.getString(2)));
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

	@Override
	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		// Connect to database
				con = db.connect();
				statement = con.prepareCall("{CALL DeleteCourse(?)}");
				
				try {
					statement.setInt(1, courseID);
					statement.execute();
					logger.info("Course:"+courseID+"Deleted successfully from the database");
				}
				catch(Exception e) {
					
					logger.error("Unable to execute query to delete course");
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
	public boolean insertCourse(Course course) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		
		// Connect to database
		con = db.connect();
		statement = con.prepareCall("{CALL Createcourse(?,?)}");
		
		try {
			statement.setInt(1, course.getCourseID());
			statement.setString(2, course.getCourseName());
			statement.execute();
			logger.info("Course:"+course.getCourseID()+"Added successfully in the database");
		}
		catch(Exception e) {
			
			logger.error("Unable to execute query to insert course");
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
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean flag = true;
		// Connect to database
				con = db.connect();
				statement = con.prepareCall("{CALL checkInstructorAssignedForCourse(?)}");
				
				try {
					statement.setInt(1, course.getCourseID());
					rs = statement.executeQuery();
					//Check if resultset is Empty
					if(rs.next() == false) {
						flag = false;
					}
					logger.info("Executed check instructor query successfully");
				}
				catch(Exception e) {
					
					logger.error("Unable to execute query to check instructor assigned for course");

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
		return flag;
	}

	@Override
	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
				boolean flag = true;
				// Connect to database
						con = db.connect();
						statement = con.prepareCall("{CALL CheckCourseAlreadyExists(?)}");
						
						try {
							statement.setInt(1, course.getCourseID());
							rs = statement.executeQuery();
							//Check if resultset is Empty
							if(rs.next() == false) {
								flag = false;
							}
							logger.info("Executed check course query successfully");
						}
						catch(Exception e) {
							
							logger.error("Unable to execute query to check if course exists");

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
				return flag;
	}

}
