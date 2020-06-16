package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public interface CoursesDao {

	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException;
	public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedSQLException;
	public ArrayList<Course> getallcourses()throws SQLException, UserDefinedSQLException;
}
