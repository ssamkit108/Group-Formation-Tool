package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
		Properties properties = SystemConfig.instance().getProperties();
		try {
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.enrollmentincourse") + "}");
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
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean flag = true;
		db = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = db.connect();
		statement = connection
				.prepareCall("{call " + properties.getProperty("procedure.checkInstructorAssignedForCourse") + "}");

		try {
			statement.setInt(1, course.getCourseID());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				flag = false;
			}
			logger.info("Executed check instructor query successfully");
		} catch (Exception e) {

			logger.error("Unable to execute query to check instructor assigned for course");
			throw new UserDefinedSQLException("Unable to execute query to check instructor assigned for course");

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
		return flag;
	}

}
