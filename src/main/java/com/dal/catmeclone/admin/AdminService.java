package com.dal.catmeclone.admin;

import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public interface AdminService {

	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException;

	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException;

	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException;

	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException;

	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException;

	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException;

	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException;

}
