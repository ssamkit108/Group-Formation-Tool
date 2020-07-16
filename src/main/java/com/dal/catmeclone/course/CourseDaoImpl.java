package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class CourseDaoImpl implements CoursesDao {

	final Logger LOGGER = Logger.getLogger(CourseDaoImpl.class.getName());
	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	private DataBaseConnection dbUtility;
	private Connection connection;

	@Override
	public Course getCourse(int courseId) throws UserDefinedException {
		Course course = null;
		CallableStatement statement = null;
		try {
			dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = dbUtility.connect();
			LOGGER.info("querying database to get the course");
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.getCourse") + "}");
			statement.setInt(1, courseId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				course = new Course(rs.getInt(1), rs.getString(2));
			}

		} catch (SQLException e) {
			LOGGER.warning("Exception occured:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured: Course doesn't exist with given Id");

		} finally {
			dbUtility.terminateStatement(statement);
			if (connection != null) {
				dbUtility.terminateConnection();
			}
		}
		return course;
	}

	@Override
	public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		CallableStatement statement = null;
		try {
			dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = dbUtility.connect();
			statement = connection.prepareCall("{call" + properties.getProperty("procedure.getCoursesForUser") + "}");
			statement.setString(1, user.getBannerId());
			statement.setString(1, user.getBannerId());
			ResultSet result = statement.executeQuery();

			Course course = null;
			while (result.next()) {
				course = new Course();
				course.setCourseID(result.getInt("courseid"));
				course.setCourseName(result.getString("coursename"));
				courseList.add(course);
			}
		} catch (SQLException e) {
			LOGGER.warning("Error occured:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured While Getting All the Courses for the user");
		} finally {
			dbUtility.terminateStatement(statement);
			dbUtility.terminateConnection();
		}
		return courseList;
	}

	@Override
	public ArrayList<Course> getallcourses() throws UserDefinedException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		CallableStatement statement = null;
		try {
			dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
			Properties properties = SystemConfig.instance().getProperties();
			connection = dbUtility.connect();
			LOGGER.info("querying database to get all the course");
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.getAllCourse") + "}");

			ResultSet result = statement.executeQuery();
			Course course = null;
			while (result.next()) {
				course = new Course();
				course.setCourseID(result.getInt("courseid"));
				course.setCourseName(result.getString("coursename"));
				courseList.add(course);
			}
		} catch (SQLException e) {
			LOGGER.warning("Error occured:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured While Getting All the Courses");
		} finally {
			dbUtility.terminateStatement(statement);
			if (connection != null) {
				dbUtility.terminateConnection();
			}
		}
		return courseList;
	}
}
