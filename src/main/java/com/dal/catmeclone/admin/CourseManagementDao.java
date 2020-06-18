package com.dal.catmeclone.admin;

import java.sql.SQLException;
import java.util.List;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public interface CourseManagementDao {
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException;

	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException;

	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException;

	public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException;
}
