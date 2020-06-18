package com.dal.catmeclone.course;

import java.util.List;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public interface CourseEnrollmentDao {

	public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedSQLException;

	public boolean hasEnrolledInCourse(String bannerId, int courseId) throws UserDefinedSQLException;

	public List<Course> getAllEnrolledCourse(User user) throws UserDefinedSQLException;

	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedSQLException;

}
