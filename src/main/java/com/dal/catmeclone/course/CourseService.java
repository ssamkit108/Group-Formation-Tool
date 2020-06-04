package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public interface CourseService {

	public Course getCourse(int courseId) throws UserDefinedSQLException, CourseException;
	public ArrayList<Course> getallcourses()throws SQLException, UserDefinedSQLException;

	
}
