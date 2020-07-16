package com.dal.catmeclone.admin;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AdminServiceImpl implements AdminService {

    final Logger LOGGER = Logger.getLogger(AdminServiceImpl.class.getName());
    private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    private AdminAbstractFactory adminAbstractFactory = abstractFactory.createAdminAbstractFactory();
    private UserProfileAbstractFactory userProfileAbstractFactory = abstractFactory.createUserProfileAbstractFactory();
    private CourseInstructorAssignmentDao courseInstructorDao;
    private CourseManagementDao courseManagementDao;
    private UserDao userDao;

    public AdminServiceImpl() {
        super();
        courseInstructorDao = adminAbstractFactory.createCourseInstructorAssignmentDao();
        courseManagementDao = adminAbstractFactory.createCourseManagementDao();
        userDao = userProfileAbstractFactory.createUserDao();
    }

    public AdminServiceImpl(CourseInstructorAssignmentDao courseInstructor, CourseManagementDao courseManagement,
                            UserDao userDao) {
        super();
        this.courseInstructorDao = courseInstructor;
        this.courseManagementDao = courseManagement;
        this.userDao = userDao;
    }

    @Override
    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
            throws Exception, UserDefinedException {
        boolean result = false;
        result = courseInstructorDao.enrollInstructorForCourse(Instructor, course, role);
        return result;
    }

    @Override
    public List<User> getAllUsers() throws UserDefinedException, Exception {
        List<User> listOfUsers = new ArrayList<User>();
        listOfUsers = userDao.getAllUsers();
        return listOfUsers;
    }

    @Override
    public List<Course> getAllCourses() throws Exception, UserDefinedException {
        List<Course> listOfCourses = new ArrayList<Course>();
        listOfCourses = courseManagementDao.getAllCourses();
        return listOfCourses;
    }

    @Override
    public boolean deleteCourse(int courseID) throws Exception, UserDefinedException {
        boolean result = false;
        result = courseManagementDao.deleteCourse(courseID);
        return result;
    }

    @Override
    public boolean insertCourse(Course course) throws UserDefinedException, Exception {
        boolean result = false;
        result = courseManagementDao.insertCourse(course);
        return result;
    }

    @Override
    public boolean checkInstructorForCourse(Course course) throws UserDefinedException, Exception {
        boolean result = false;
        result = courseInstructorDao.checkInstructorForCourse(course);
        return result;
    }

    @Override
    public boolean checkCourseExists(Course course) throws UserDefinedException, Exception {
        boolean result = false;
        result = courseManagementDao.checkCourseExists(course);
        return result;
    }

}
