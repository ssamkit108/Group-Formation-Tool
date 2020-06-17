package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentDaoImpl implements CourseInstructorAssignmentDao {

	DataBaseConnection db;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);
	private Connection connection;
	private CallableStatement statement;

	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		db = SystemConfig.instance().getDatabaseConnection();
		connection = db.connect();
		try {
			statement = connection.prepareCall("{call enrollmentincourse(?,?,?)}");
			statement.setString(1, Instructor.getBannerId());
			statement.setInt(2, course.getCourseID());
			statement.setString(3, role.getRoleName());
			statement.execute();
			logger.info("Course:" + Instructor.getBannerId() + course.getCourseID() + role.getRoleName()
					+ "Instructor Enrolled successfully");

		} catch (SQLException e) {
			logger.error("Unable to execute query to check if course exists");
			e.printStackTrace();
			return false;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
		return true;
	}

	@Override
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException {
		List<User> listOfUsers;
		ResultSet resultSet;
		listOfUsers = new ArrayList<User>();
		db = SystemConfig.instance().getDatabaseConnection();
		connection = db.connect();
		try {
			statement = connection.prepareCall("{CALL GetAllUsers()}");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listOfUsers.add(new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
			}
			logger.info("Retrieved successfully from the database");
		} catch (Exception e) {
			logger.error("Unable to execute query to get all courses");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		}
		return listOfUsers;
	}

}
