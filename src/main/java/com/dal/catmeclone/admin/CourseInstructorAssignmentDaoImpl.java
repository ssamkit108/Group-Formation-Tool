package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentDaoImpl implements CourseInstructorAssignmentDao {

	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	DBUtilityAbstractFactory dbUtilityAbstractFactory=abstractFactory.createDBUtilityAbstractFactory();

	DataBaseConnection DBUtil;
	final Logger LOGGER = LoggerFactory.getLogger(CourseInstructorAssignmentDaoImpl.class);
	private Connection connection;
	private CallableStatement statement;


	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role) throws UserDefinedSQLException {
		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		connection = DBUtil.connect();
		Properties properties = SystemConfig.instance().getProperties();
		try {
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.enrollmentincourse") + "}");
			statement.setString(1, Instructor.getBannerId());
			statement.setInt(2, course.getCourseID());
			statement.setString(3, role.getRoleName());
			statement.execute();
			LOGGER.info("Course:" + Instructor.getBannerId() + course.getCourseID() + role.getRoleName()
					+ "Instructor Enrolled successfully");

		} catch (SQLException e) {
			LOGGER.error("Unable to execute query to check if course exists");
			e.printStackTrace();
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
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean flag = true;
		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = DBUtil.connect();
		statement = connection
				.prepareCall("{call " + properties.getProperty("procedure.checkInstructorAssignedForCourse") + "}");

		try {
			statement.setInt(1, course.getCourseID());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				flag = false;
			}
			LOGGER.info("Executed check instructor query successfully");
		} catch (Exception e) {
			LOGGER.error("Unable to execute query to check instructor assigned for course");
			throw new UserDefinedSQLException("Unable to execute query to check instructor assigned for course");

		} finally {
			DBUtil.terminateStatement(statement);
			if (connection != null) {
				DBUtil.terminateConnection();
			}
		}
		return flag;
	}

}
