package com.dal.catmeclone.courses;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dal.catmeclone.DBUtility.DatabaseConnection;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Component
public class CourseDaoImpl implements CoursesDao {
	
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	@Value("${procedure.getCoursesForUser}")
	private String getCoursesForUser;
	
	@Value("${procedure.getUserRoleforCourse}")
	private String getUserRoleforCourse;
	
	@Value("${procedure.getCourse}")
	private String getCourse;
	
	private Connection connection;
	
	@Override
	public List<Course> getAllEnrolledCourse(User user) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		List<Course> listofCourses = new ArrayList<Course>();
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			
			statement = connection.prepareCall("{call "+getCoursesForUser+"}");
		
			statement.setString(1, user.getBannerId());
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				listofCourses.add(new Course(rs.getInt(1), rs.getString(2)));
			}
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("provide logger here");
			return listofCourses;
		}finally {
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return listofCourses;
	}

	@Override
	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		Role role=null;
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			
			statement = connection.prepareCall("{call "+getUserRoleforCourse+"}");
		
			statement.setString(1, user.getBannerId());
			statement.setInt(2, course.getCourseID());
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				role = new Role(rs.getInt(1), rs.getString(2));
			}
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("provide logger here");
			return role;
		}finally {
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return role;
	}

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		Course course=null;
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			
			statement = connection.prepareCall("{call "+getCourse+"}");
		
			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				course = new Course(rs.getInt(1), rs.getString(2));
			}
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CourseException("Error Occured: Course doesn't exist with given Id");
			
		} finally {
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return course;
	}

}
