package com.dal.catmeclone.course;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

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
		// TODO Auto-generated method stub
		Course course=null;
		course = courseDB.getCourse(courseId);		
		return course;
	}

	

}
