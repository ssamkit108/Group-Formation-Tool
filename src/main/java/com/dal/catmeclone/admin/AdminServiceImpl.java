package com.dal.catmeclone.admin;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    AdminAbstractFactory adminAbstractFactory = abstractFactory.createAdminAbstractFactory();
    UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    private CourseInstructorAssignmentDao courseInstructor;
    private CourseManagementDao courseManagement;
    private UserDao userDao;

    @Override
    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
            throws SQLException, UserDefinedSQLException {
        boolean result = false;
        courseInstructor = adminAbstractFactory.createCourseInstructorAssignmentDao();
        result = courseInstructor.enrollInstructorForCourse(Instructor, course, role);

        return result;
    }

    @Override
    public List<User> getAllUsers() throws SQLException, UserDefinedSQLException {
        List<User> listOfUsers = new ArrayList<User>();
        userDao = userProfileAbstractFactory.createUserDao();
        listOfUsers = userDao.getAllUsers();

        return listOfUsers;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException {
        List<Course> listOfCourses = new ArrayList<Course>();
        courseManagement = adminAbstractFactory.createCourseManagementDao();
        listOfCourses = courseManagement.getAllCourses();

        return listOfCourses;
    }

    @Override
    public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
        boolean result = false;
        courseManagement = adminAbstractFactory.createCourseManagementDao();
        result = courseManagement.deleteCourse(courseID);

        return result;
    }

    @Override
    public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException {
        boolean result = false;
        courseManagement = adminAbstractFactory.createCourseManagementDao();
        result = courseManagement.insertCourse(course);

        return result;
    }

    @Override
    public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
        boolean result = false;
        courseInstructor = adminAbstractFactory.createCourseInstructorAssignmentDao();
        result = courseInstructor.checkInstructorForCourse(course);

        return result;
    }

    @Override
    public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
        boolean result = false;
        courseManagement = adminAbstractFactory.createCourseManagementDao();
        result = courseManagement.checkCourseExists(course);

        return result;
    }

}
