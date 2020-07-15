package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class CourseDaoImpl implements CoursesDao {

    final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    private DataBaseConnection DBUtil;
    private Connection connection;

    @Override
    public Course getCourse(int courseId) throws UserDefinedSQLException, CourseException {
        Course course = null;
        CallableStatement statement = null;
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            logger.info("querying database to get the course");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.getCourse") + "}");
            statement.setInt(1, courseId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                course = new Course(rs.getInt(1), rs.getString(2));
            }

        } catch (SQLException e) {

            logger.error("Exception occured:" + e.getLocalizedMessage());
            throw new CourseException("Error Occured: Course doesn't exist with given Id");

        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return course;
    }

    @Override
    public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedSQLException {
        ArrayList<Course> courseList = new ArrayList<Course>();
        CallableStatement statement = null;
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
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
            throw new UserDefinedSQLException("Some error occured");
        } finally {

            DBUtil.terminateStatement(statement);
            DBUtil.terminateConnection();
        }
        return courseList;
    }

    @Override
    public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
        ArrayList<Course> courseList = new ArrayList<Course>();
        CallableStatement statement = null;
        try {
            DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
            Properties properties = SystemConfig.instance().getProperties();
            connection = DBUtil.connect();
            logger.info("querying database to get all the course");
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
            logger.error("Exception occured:" + e.getLocalizedMessage());
            return null;
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return courseList;
    }
}
