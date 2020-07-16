package com.dal.catmeclone.admin;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface AdminService {

    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
            throws UserDefinedSQLException, Exception;

    public List<User> getAllUsers() throws UserDefinedSQLException, Exception;

    public List<Course> getAllCourses() throws UserDefinedSQLException, Exception;

    public boolean deleteCourse(int courseID) throws UserDefinedSQLException, Exception;

    public boolean insertCourse(Course course) throws UserDefinedSQLException, Exception;

    public boolean checkInstructorForCourse(Course course) throws UserDefinedSQLException, Exception;

    public boolean checkCourseExists(Course course) throws UserDefinedSQLException, Exception;

}
