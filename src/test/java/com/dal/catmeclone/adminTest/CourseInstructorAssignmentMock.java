package com.dal.catmeclone.adminTest;

import java.sql.SQLException;

import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentMock implements CourseInstructorAssignmentDao {

    
	@Override
	public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
			throws SQLException, UserDefinedSQLException {
		
		return true;
	}

	@Override
	public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, SQLException {
		return true;
	}

}
