package com.dal.catmeclone.course;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

import java.util.ArrayList;

public interface CoursesDao {

    public Course getCourse(int courseId) throws UserDefinedException;

    public ArrayList<Course> getallcoursesbyuser(User user) throws UserDefinedException;

    public ArrayList<Course> getallcourses() throws UserDefinedException;
}
