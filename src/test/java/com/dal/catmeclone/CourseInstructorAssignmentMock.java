package com.dal.catmeclone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentMock implements CourseInstructorAssignmentDao{

	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		return true;
	}

	@Override
	public List<User> getAllUsers() throws SQLException, UserDefinedSQLException {
		List<User> l = new ArrayList<User>();
		l.add(new User("B0085231","saky","damu","asdac","asd@gmail.com"));
		return l;
	}

}
