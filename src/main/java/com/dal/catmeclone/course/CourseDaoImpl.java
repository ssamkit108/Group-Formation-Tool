package com.dal.catmeclone.course;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

@Component
public class CourseDaoImpl implements CoursesDao {
	
	final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	
	
	@Value("${procedure.getCourse}")
	private String getCourse;
	
	private Connection connection;
	
	

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		Course course=null;
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			logger.info("querying database to get the course");
			statement = connection.prepareCall("{call "+getCourse+"}");
		
			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				course = new Course(rs.getInt(1), rs.getString(2));
			}
			
						
		} catch (SQLException e) {
			
			logger.error("Exception occured:" +e.getLocalizedMessage());
			throw new CourseException("Error Occured: Course doesn't exist with given Id");
			
		} finally {
			DBUtil.terminateStatement(statement);
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return course;
	}
	
	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		ArrayList<Course> allcrclst = new ArrayList<Course>();
		CallableStatement stored_pro = null;
		try {

			connection = DBUtil.connect();
			logger.info("querying database to get all the course");
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
			logger.error("Exception occured:" +e.getLocalizedMessage());
			return null;
		}
		finally
		{
			DBUtil.terminateStatement(stored_pro);
			if(connection!=null) {
			DBUtil.terminateConnection();
			}
		}
		return allcrclst;
	}

}
