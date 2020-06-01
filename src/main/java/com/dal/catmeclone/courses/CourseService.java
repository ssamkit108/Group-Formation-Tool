package com.dal.catmeclone.courses;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public interface CourseService {

	public Course getCourse(int courseId) throws UserDefinedSQLException, CourseException;
	public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedSQLException;
	public Role getUserRoleForCourse(User user,Course course) throws UserDefinedSQLException;

	
}
