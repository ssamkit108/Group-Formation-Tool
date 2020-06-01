/**
 * 
 */
package com.dal.catmeclone.courses;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

/**
 * @author Mayank
 *
 */
public interface CourseEnrollmentDao {

	public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedSQLException;
	public boolean hasEnrolledInCourse(String bannerId, int courseId) throws UserDefinedSQLException;
	public List<User> getTaForCourse(Course course) throws UserDefinedSQLException;
	
}
