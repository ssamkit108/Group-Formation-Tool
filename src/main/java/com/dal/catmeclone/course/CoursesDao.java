package com.dal.catmeclone.course;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public interface CoursesDao {

	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException;
	
}
