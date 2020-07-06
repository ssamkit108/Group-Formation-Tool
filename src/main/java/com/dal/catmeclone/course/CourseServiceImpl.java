package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dal.catmeclone.AbstractFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public class CourseServiceImpl implements CourseService {

	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	CourseAbstractFactory courseAbstractFactory=abstractFactory.createCourseAbstractFactory();
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

	CoursesDao courseDB;

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException, CourseException {
		// Calling DAO Method to get the course
		courseDB = courseAbstractFactory.createCourseDao();
		Course course = null;
		LOGGER.info("Calling Dao to get the course");
		course = courseDB.getCourse(courseId);
		return course;
	}

	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		// Calling DAO Method to fetch the list
		courseDB = courseAbstractFactory.createCourseDao();
		ArrayList<Course> courseList = new ArrayList<Course>();
		LOGGER.info("Calling Dao to get the list of course");
		courseList = courseDB.getallcourses();
		return courseList;
	}
}
