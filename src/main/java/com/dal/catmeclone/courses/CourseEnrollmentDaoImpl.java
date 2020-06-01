/**
 * 
 */
package com.dal.catmeclone.courses;

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
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.useraccess.UserDao;

/**
 * @author Mayank
 *
 */
@Component
public class CourseEnrollmentDaoImpl implements CourseEnrollmentDao {
	
	final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	@Autowired
	private DatabaseConnection DBUtil;
	
	@Value("${procedure.searchUserInUserCourseRole}")
	private String searchUserInUserCourseForRole;
	
	@Value("${procedure.enrollmentincourse}")
	private String enrollmentincourse;
	
	@Value("${procedure.getTAForCourse}")
	private String getTAForCourse;
	
	private Connection connection;

	@Override
	public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			
			statement = connection.prepareCall("{call "+enrollmentincourse+"}");
		
			statement.setString(1, student.getBannerId());
			statement.setInt(2, course.getCourseID());
			statement.setString(3, role.getRoleName());
			System.out.println(student.getBannerId()+","+course.getCourseID());
			statement.execute();
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally {
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
			e.printStackTrace();
			return false;
		} finally {
			
			if (connection != null) {
					DBUtil.terminateConnection();
			}
		}

	}

	@Override
	public List<User> getTaForCourse(Course course) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		List<User> listOfTA = new ArrayList<User>();
		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();

			statement = connection.prepareCall("{call " + getTAForCourse + "}");

			statement.setInt(1, course.getCourseID());
			statement.setString(2, "TA");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				User user= new User(rs.getString("bannerid"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"));
				listOfTA.add(user);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserDefinedSQLException(e.getLocalizedMessage());
		} finally {
			
			if (connection != null) {
					DBUtil.terminateConnection();
			}
		}
		return listOfTA;
	}

}
