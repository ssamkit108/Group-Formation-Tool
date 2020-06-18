package com.dal.catmeclone.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		boolean res = false;
		courseInstructor = SystemConfig.instance().getCourseInstructorAssignmentDao();
		res = courseInstructor.enrollInstructorForCourse(Instructor, course, role);

		return res;
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
		boolean res = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		res = courseManagement.deleteCourse(courseID);

		return res;
	}

	@Override
	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		res = courseManagement.insertCourse(course);

		return res;
	}

	@Override
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		courseInstructor = SystemConfig.instance().getCourseInstructorAssignmentDao();
		res = courseInstructor.checkInstructorForCourse(course);

		return res;
	}

	@Override
	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		courseManagement = SystemConfig.instance().getCourseManagementDao();
		res = courseManagement.checkCourseExists(course);

		return res;
	}

}
