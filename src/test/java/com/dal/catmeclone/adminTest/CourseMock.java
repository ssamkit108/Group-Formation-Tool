package com.dal.catmeclone.adminTest;

import com.dal.catmeclone.admin.CourseManagementDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseMock implements CourseManagementDao {

    @Override
    public List<Course> getAllCourses() throws SQLException, UserDefinedSQLException {
        List<Course> l = new ArrayList<Course>();
        l.add(new Course(123, "sdc"));
        return l;
    }

    @Override
    public boolean deleteCourse(int courseID) throws SQLException, UserDefinedSQLException {
        return true;
    }

    @Override
    public boolean insertCourse(Course course) throws UserDefinedSQLException, SQLException {
        return true;
    }

    @Override
    public boolean checkCourseExists(Course course) throws UserDefinedSQLException, SQLException {
        return true;
    }

}

