/**
 * 
 */
package com.dal.catmeclone.dao;

import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

/**
 * @author amith
 *
 */
public interface CourseDao {
	public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException;
	public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException;
	public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException;
}
