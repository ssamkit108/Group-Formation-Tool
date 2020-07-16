package com.dal.catmeclone.admin;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseManagementDao {
    public List<Course> getAllCourses() throws SQLException, UserDefinedException;

    public boolean deleteCourse(int courseID) throws Exception;

    public boolean insertCourse(Course course) throws UserDefinedException, SQLException, Exception;

    public boolean checkCourseExists(Course course) throws UserDefinedException, SQLException, Exception;
}
