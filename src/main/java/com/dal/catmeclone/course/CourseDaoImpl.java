package com.dal.catmeclone.course;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;


public class CourseDaoImpl implements CoursesDao {
	
	final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
	
	private DataBaseConnection DBUtil;
	
	private Connection connection;
	
	

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		Course course=null;
		CallableStatement statement = null;
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			logger.info("querying database to get the course");
			statement = connection.prepareCall("{call "+properties.getProperty("procedure.getCourse")+"}");
		
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
	public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedSQLException {
		ArrayList<Course> crclst = new ArrayList<Course>();
		CallableStatement stored_pro = null;
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			stored_pro = connection.prepareCall("{call"+ properties.getProperty("procedure.getCoursesForUser")+"}");
			
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

			DBUtil.terminateStatement(stored_pro);
			DBUtil.terminateConnection();
		}
		return crclst;




	}

	
	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		ArrayList<Course> allcrclst = new ArrayList<Course>();
		CallableStatement stored_pro = null;
		try {
			DBUtil = SystemConfig.instance().getDatabaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = DBUtil.connect();
			logger.info("querying database to get all the course");
			stored_pro = connection.prepareCall("{call"+ properties.getProperty("procedure.getAllCourse")+"}");
			
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
