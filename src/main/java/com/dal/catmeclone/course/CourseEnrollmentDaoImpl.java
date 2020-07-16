package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class CourseEnrollmentDaoImpl implements CourseEnrollmentDao {

    final static Logger LOGGER = Logger.getLogger(CourseEnrollmentDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    private DataBaseConnection dbUtility;
    private Connection connection;

    @Override
    public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedException {
        CallableStatement statement = null;
        try {
            dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = dbUtility.connect();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.enrollmentincourse") + "}");

            statement.setString(1, student.getBannerId());
            statement.setInt(2, course.getCourseID());
            statement.setString(3, role.getRoleName());

            statement.execute();
            LOGGER.info("User enrolled in the Courrse");
        } catch (SQLException e) {
        	LOGGER.warning("error occured: "+e.getLocalizedMessage());
            throw new UserDefinedException("Error Occured while process Request. Please try again later");
        } finally {
            dbUtility.terminateStatement(statement);
            if (connection != null) {
                dbUtility.terminateConnection();
            }
        }
        return true;
    }

    @Override
    public boolean hasEnrolledInCourse(String bannerId, int courseId) throws UserDefinedException {
        CallableStatement statement = null;
        try {
            dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = dbUtility.connect();
            LOGGER.info("checking if user is enrolled in course in database");

            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.searchUserInUserCourseRole") + "}");

            statement.setString(1, bannerId);
            statement.setInt(2, courseId);
            statement.setString(3, "Student");

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
        	LOGGER.warning("error occured: "+e.getLocalizedMessage());
            throw new UserDefinedException("Error Occured while process Request. Please try again later");
        } finally {
            dbUtility.terminateStatement(statement);
            if (connection != null) {
                dbUtility.terminateConnection();
            }
        }
    }

    @Override
    public List<Course> getAllEnrolledCourse(User user) throws UserDefinedException {
        List<Course> listofCourses = new ArrayList<Course>();
        CallableStatement statement = null;
        try {
            dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = dbUtility.connect();
           
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.getCoursesForUser") + "}");

            statement.setString(1, user.getBannerId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listofCourses.add(new Course(resultSet.getInt(1), resultSet.getString(2)));
            }

        } catch (SQLException e) {
        	LOGGER.warning("error occured: "+e.getLocalizedMessage());
            throw new UserDefinedException("Error Occured while process Request. Please try again later");
        } finally {
            dbUtility.terminateStatement(statement);
            if (connection != null) {
                dbUtility.terminateConnection();
            }
        }
        return listofCourses;
    }

    @Override
    public Role getUserRoleForCourse(User user, Course course) throws UserDefinedException {
        Role role = null;
        CallableStatement statement = null;
        try {
            dbUtility = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = dbUtility.connect();

            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getUserRoleforCourse") + "}");

            statement.setString(1, user.getBannerId());
            statement.setInt(2, course.getCourseID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                role = new Role(resultSet.getInt(1), resultSet.getString(2));
            }

        } catch (SQLException e) {
        	LOGGER.warning("error occured: "+e.getLocalizedMessage());
            throw new UserDefinedException("Error Occured while process Request. Please try again later");
        } finally {
            dbUtility.terminateStatement(statement);
            if (connection != null) {
                dbUtility.terminateConnection();
            }
        }
        return role;
    }

}
