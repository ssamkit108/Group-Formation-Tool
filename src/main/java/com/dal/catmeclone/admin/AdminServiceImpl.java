package com.dal.catmeclone.admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	CourseInstructorAssignmentDao courseInstructor;
	
	@Autowired
	CourseManagementDao courseManagement;
	
	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		boolean res = false;
		res = courseInstructor.enrollInstructorForCourse(Instructor, course, role);
		
		return res;
	}

	@Override
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException {
		List<User> listOfUsers = new ArrayList<User>();
		listOfUsers = courseInstructor.getAllUsers();
		
		return listOfUsers;
	}

	@Override
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException {
		List<Course> listOfCourses = new ArrayList<Course>();
		listOfCourses = courseManagement.getAllCourses();
		
		return listOfCourses;
	}

	@Override
	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
		boolean res = false;
		res = courseManagement.deleteCourse(courseID);
		return res;
	}

	@Override
	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		res = courseManagement.insertCourse(course);
		return res;
	}

	@Override
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		res = courseManagement.checkInstructorForCourse(course);
		return res;
	}

	@Override
	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
		boolean res = false;
		res = courseManagement.checkCourseExists(course);
		return res;
	}

}
