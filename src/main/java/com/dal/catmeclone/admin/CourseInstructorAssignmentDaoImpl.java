package com.dal.catmeclone.admin;

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
import java.util.Properties;
import java.util.logging.Logger;

public class CourseInstructorAssignmentDaoImpl implements CourseInstructorAssignmentDao {

    Logger LOGGER = Logger.getLogger(CourseInstructorAssignmentDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    DataBaseConnection DBUtil;
    private Connection connection;
    private CallableStatement statement;


    @Override
    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role) throws UserDefinedException {
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
            LOGGER.warning("Unable to execute query to check if course exists");
            throw new UserDefinedException("Unable to execute query to check if course exists" + e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return true;
    }

    @Override
    public boolean checkInstructorForCourse(Course course) throws UserDefinedException, Exception {
        boolean flag = true;
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        connection = DBUtil.connect();
        statement = connection.prepareCall("{call " + properties.getProperty("procedure.checkInstructorAssignedForCourse") + "}");
        try {
            statement.setInt(1, course.getCourseID());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == false) {
                flag = false;
            }
            LOGGER.info("Executed check instructor query successfully");
        } catch (SQLException e) {
            LOGGER.warning("Unable to execute query to check instructor assigned for course");
            throw new UserDefinedException("Unable to execute query to check instructor assigned for course");
        } catch (Exception e) {
            LOGGER.severe("Generic Exception generated in " + CourseInstructorAssignmentDaoImpl.class);
            throw new Exception("Generic Exception generated in " + e.getLocalizedMessage());
        } finally {
            DBUtil.terminateStatement(statement);
            if (connection != null) {
                DBUtil.terminateConnection();
            }
        }
        return flag;
    }

}
