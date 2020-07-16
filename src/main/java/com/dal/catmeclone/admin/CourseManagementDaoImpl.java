package com.dal.catmeclone.admin;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class CourseManagementDaoImpl implements CourseManagementDao {

    java.util.logging.Logger LOGGER = Logger.getLogger(CourseManagementDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    DataBaseConnection DBUtil;
    List<Course> listOfCourses;
    private CallableStatement statement;
    private Connection connection;
    private ResultSet resultSet;

    @Override
    public List<Course> getAllCourses() throws SQLException, UserDefinedException {
        listOfCourses = new ArrayList<Course>();
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        try {
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.getAllCourse") + "}");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listOfCourses.add(new Course(resultSet.getInt(1), resultSet.getString(2)));
            }
            LOGGER.info("Retrieved successfully from the database");
        } catch (Exception e) {
            LOGGER.warning("Unable to execute query to get all courses");
            throw new UserDefinedException("Some Error occurred in executing query");
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return listOfCourses;
    }

    @Override
    public boolean deleteCourse(int courseID) throws UserDefinedException, Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        statement = connection.prepareCall("{call " + properties.getProperty("procedure.DeleteCourse") + "}");
        try {
            statement.setInt(1, courseID);
            statement.execute();
            LOGGER.info("Course:" + courseID + "Deleted successfully from the database");
        } catch (SQLException e) {
            LOGGER.warning("SQL Exception generated: " + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Exception generated: " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic Exception generated: " + e.getLocalizedMessage());
            throw new Exception("Generic Exception generated: " + e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return true;
    }

    @Override
    public boolean insertCourse(Course course) throws Exception, UserDefinedException {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        statement = connection.prepareCall("{call " + properties.getProperty("procedure.Createcourse") + "}");
        try {
            statement.setInt(1, course.getCourseID());
            statement.setString(2, course.getCourseName());
            statement.execute();
            LOGGER.info("Course:" + course.getCourseID() + "Added successfully in the database");
        } catch (SQLException e) {
            LOGGER.warning("SQL Exception generated: " + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Exception generated: " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic Exception generated: " + e.getLocalizedMessage());
            throw new Exception("Generic Exception generated: " + e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return true;
    }

    @Override
    public boolean checkCourseExists(Course course) throws UserDefinedException, Exception {
        boolean flag = true;
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        statement = connection
                .prepareCall("{call " + properties.getProperty("procedure.CheckCourseAlreadyExists") + "}");

        try {
            statement.setInt(1, course.getCourseID());
            resultSet = statement.executeQuery();
            if (resultSet.next() == false) {
                flag = false;
            }
            LOGGER.info("Executed check course query successfully");
        } catch (SQLException e) {
            LOGGER.warning("SQL Exception generated: " + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Exception generated: " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic Exception generated: " + e.getLocalizedMessage());
            throw new Exception("Generic Exception generated: " + e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return flag;
    }

}
