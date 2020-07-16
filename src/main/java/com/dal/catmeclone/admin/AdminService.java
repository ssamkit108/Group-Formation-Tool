package com.dal.catmeclone.admin;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface AdminService {

    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
            throws UserDefinedException, Exception;

    public List<User> getAllUsers() throws UserDefinedException, Exception;

    public List<Course> getAllCourses() throws UserDefinedException, Exception;

    public boolean deleteCourse(int courseID) throws UserDefinedException, Exception;

    public boolean insertCourse(Course course) throws UserDefinedException, Exception;

    public boolean checkInstructorForCourse(Course course) throws UserDefinedException, Exception;

    public boolean checkCourseExists(Course course) throws UserDefinedException, Exception;

}
