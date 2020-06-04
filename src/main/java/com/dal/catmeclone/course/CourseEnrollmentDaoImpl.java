/**
 * 
 */
package com.dal.catmeclone.course;

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
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

/**
 * @author Mayank
 *
 */
@Component
public class CourseEnrollmentDaoImpl implements CourseEnrollmentDao {

	final Logger logger = LoggerFactory.getLogger(CourseEnrollmentDaoImpl.class);

	@Autowired
	private DatabaseConnection DBUtil;

	@Value("${procedure.searchUserInUserCourseRole}")
	private String searchUserInUserCourseForRole;

	@Value("${procedure.enrollmentincourse}")
	private String enrollmentincourse;
	
	@Value("${procedure.getCoursesForUser}")
	private String getCoursesForUser;
	
	@Value("${procedure.getUserRoleforCourse}")
	private String getUserRoleforCourse;

	private Connection connection;

	@Override
	public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();

			statement = connection.prepareCall("{call " + enrollmentincourse + "}");

			statement.setString(1, student.getBannerId());
			statement.setInt(2, course.getCourseID());
			statement.setString(3, role.getRoleName());

			statement.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		} finally {	
			DBUtil.terminateStatement(statement);
			if (connection != null) {

				DBUtil.terminateConnection();

			}
		}
		return true;
	}

	@Override
	public boolean hasEnrolledInCourse(String bannerId, int courseId) throws UserDefinedSQLException {
		// TODO Auto-generated method stub

		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();

			statement = connection.prepareCall("{call " + searchUserInUserCourseForRole + "}");

			statement.setString(1, bannerId);
			statement.setInt(2, courseId);
			statement.setString(3, "Student");

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		} finally {
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}

	}

	
	
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
			return listofCourses;
		}finally {
			DBUtil.terminateStatement(statement);
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
			DBUtil.terminateStatement(statement);
			if (connection != null) {	
					DBUtil.terminateConnection();
			}
		}
		return role;
	}

}
