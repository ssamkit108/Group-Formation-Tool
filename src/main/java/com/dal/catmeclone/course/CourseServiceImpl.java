package com.dal.catmeclone.course;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class CourseServiceImpl implements CourseService {

    private static final Logger LOGGER = Logger.getLogger(CourseServiceImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    CourseAbstractFactory courseAbstractFactory = abstractFactory.createCourseAbstractFactory();
    CoursesDao courseDao;

    public CourseServiceImpl() {
        super();
        courseDao = courseAbstractFactory.createCourseDao();
    }

    public CourseServiceImpl(CoursesDao courseDao) {
        super();
        this.courseDao = courseDao;
    }

    @Override
    public Course getCourse(int courseId) throws UserDefinedException {
        // Calling DAO Method to get the course
        Course course = null;
        LOGGER.info("Calling Dao to get the course");
        course = courseDao.getCourse(courseId);
        return course;
    }

    @Override
    public ArrayList<Course> getallcourses() throws SQLException, UserDefinedException {
        // Calling DAO Method to fetch the list
        ArrayList<Course> courseList = new ArrayList<Course>();
        LOGGER.info("Calling Dao to get the list of course");
        courseList = courseDao.getallcourses();
        return courseList;
    }
}
