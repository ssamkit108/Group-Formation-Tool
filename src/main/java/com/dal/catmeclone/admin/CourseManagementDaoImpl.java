package com.dal.catmeclone.admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.*;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public class CourseManagementDaoImpl implements CourseManagementDao {

	DataBaseConnection db;
	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);
	private CallableStatement statement;
	private Connection connection;
	private ResultSet resultSet;
	List<Course> listOfCourses;

	@Override
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException {
		listOfCourses = new ArrayList<Course>();
		db = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = db.connect();
		try {
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.getAllCourse") + "}");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listOfCourses.add(new Course(resultSet.getInt(1), resultSet.getString(2)));
			}
			logger.info("Retrieved successfully from the database");
		} catch (Exception e) {
			logger.error("Unable to execute query to get all courses");
			throw new UserDefinedSQLException("Some Error occured in executig query");
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
		return listOfCourses;
	}

	@Override
	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		// Connect to database
		db = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = db.connect();
		statement = connection
				.prepareCall("{call " + properties.getProperty("procedure.DeleteCourse") + "}");


		try {
			statement.setInt(1, courseID);
			statement.execute();
			logger.info("Course:" + courseID + "Deleted successfully from the database");
		} catch (Exception e) {

			logger.error("Unable to execute query to delete course");
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
	public boolean insertCourse(Course course) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub

		// Connect to database
		db = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = db.connect();
		statement = connection
				.prepareCall("{call " + properties.getProperty("procedure.Createcourse") + "}");
		try {
			statement.setInt(1, course.getCourseID());
			statement.setString(2, course.getCourseName());
			statement.execute();
			logger.info("Course:" + course.getCourseID() + "Added successfully in the database");
		} catch (Exception e) {
			logger.error("Unable to execute query to insert course");
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
			resultSet = statement.executeQuery();
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

	@Override
	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
		boolean flag = true;
		db = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		connection = db.connect();
		statement = connection
				.prepareCall("{call " + properties.getProperty("procedure.CheckCourseAlreadyExists") + "}");

		try {
			statement.setInt(1, course.getCourseID());
			resultSet = statement.executeQuery();
			if (resultSet.next() == false) {
				flag = false;
			}
			logger.info("Executed check course query successfully");
		} catch (Exception e) {

			logger.error("Unable to execute query to check if course exists");
			throw new UserDefinedSQLException("Unable to execute query to check if course exists");
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
