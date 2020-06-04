package com.dal.catmeclone.course;

import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

/*
 * 
 */
@Service
public class CourseServiceImpl implements CourseService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
	
	@Autowired
	CoursesDao courseDB;
	

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// Calling DAO Method to get the course
		Course course=null;
		LOGGER.info("Calling Dao to get the course");
		course = courseDB.getCourse(courseId);		
		return course;
	}


	@Override
	public ArrayList<Course> getallcourses() throws SQLException, UserDefinedSQLException {
		// Calling DAO Method to fetch the list
		ArrayList<Course> courseList = new ArrayList<Course>();
		LOGGER.info("Calling Dao to get the list of course");
		courseList = courseDB.getallcourses();
		return courseList;
	}

	

}
