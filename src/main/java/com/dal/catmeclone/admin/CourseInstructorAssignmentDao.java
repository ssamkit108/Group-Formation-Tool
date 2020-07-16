package com.dal.catmeclone.admin;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

import java.sql.SQLException;

public interface CourseInstructorAssignmentDao {

    public boolean enrollInstructorForCourse(User Instructor, Course course, Role role)
            throws SQLException, UserDefinedException;

    public boolean checkInstructorForCourse(Course course) throws UserDefinedException, SQLException, Exception;

}
