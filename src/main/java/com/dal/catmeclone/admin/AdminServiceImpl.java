package com.dal.catmeclone.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.UserProfile.UserDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class AdminServiceImpl implements AdminService {

	private CourseInstructorAssignmentDao courseInstructor;
	private CourseManagementDao courseManagement;
	private UserDao userDao;
	final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		boolean result = false;
		courseInstructor = SystemConfig.instance().getCourseInstructorAssignmentDao();
		result = courseInstructor.enrollInstructorForCourse(Instructor, course, role);

		return result;
	}

	@Override
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException {
		List<User> listOfUsers = new ArrayList<User>();
		userDao = SystemConfig.instance().getUserDao();
		listOfUsers = userDao.getAllUsers();

		return listOfUsers;
	}

	@Override
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException {
		List<Course> listOfCourses = new ArrayList<Course>();
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		listOfCourses = courseManagement.getAllCourses();

		return listOfCourses;
	}

	@Override
	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
		boolean result = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		result = courseManagement.deleteCourse(courseID);

		return result;
	}

	@Override
	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean result = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		result = courseManagement.insertCourse(course);

		return result;
	}

	@Override
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean result = false;
		courseInstructor = SystemConfig.instance().getCourseInstructorAssignmentDao();
		result = courseInstructor.checkInstructorForCourse(course);

		return result;
	}

	@Override
	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
		boolean result = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		result = courseManagement.checkCourseExists(course);

		return result;
	}

}
