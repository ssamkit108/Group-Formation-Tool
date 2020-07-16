package com.dal.catmeclone.course;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface CourseEnrollmentDao {

    public boolean enrollUserForCourse(User student, Course course, Role role) throws UserDefinedException;

    public boolean hasEnrolledInCourse(String bannerId, int courseId) throws UserDefinedException;

    public List<Course> getAllEnrolledCourse(User user) throws UserDefinedException;

    public Role getUserRoleForCourse(User user, Course course) throws UserDefinedException;

}
