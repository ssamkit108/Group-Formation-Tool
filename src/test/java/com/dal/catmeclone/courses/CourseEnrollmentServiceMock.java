package com.dal.catmeclone.courses;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dal.catmeclone.course.CourseEnrollmentService;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseEnrollmentServiceMock implements CourseEnrollmentService{

	@Override
	public boolean enrollStudentForCourse(MultipartFile file, Course course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean enrollTAForCourse(User Ta, Course course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRecordsSuccessMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRecordsFailureMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
