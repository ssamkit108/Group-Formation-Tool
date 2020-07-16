package com.dal.catmeclone.course;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CourseService {

    public Course getCourse(int courseId) throws UserDefinedException;

    public ArrayList<Course> getallcourses() throws SQLException, UserDefinedException;

}
