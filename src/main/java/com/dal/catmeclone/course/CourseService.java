package com.dal.catmeclone.course;

import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseService {

    public Course getCourse(int courseId) throws UserDefinedSQLException, CourseException;

    public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException;

}
