package com.dal.catmeclone.courses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	CoursesDao courseDB;
	

	@Override
	public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		List<Course> listofCourses = new ArrayList<Course>();
		listofCourses= courseDB.getAllEnrolledCourse(user);
		
		return listofCourses;
		
	}

	@Override
	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		Role role=null;
		role = courseDB.getUserRoleForCourse(user, course);
		
		return role;
	}

	@Override
	public Course getCourse(int courseId) throws UserDefinedSQLException,CourseException {
		// TODO Auto-generated method stub
		Course course=null;
		course = courseDB.getCourse(courseId);		
		return course;
	}

	

}
