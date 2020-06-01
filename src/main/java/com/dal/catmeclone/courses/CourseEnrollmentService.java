package com.dal.catmeclone.courses;


import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


public interface CourseEnrollmentService {

	
	public boolean enrollStudentForCourse(MultipartFile file, Course course);
	public boolean enrollTAForCourse(User Ta, Course course);
	public List<User> getTAForCourse(Course course) throws UserDefinedSQLException, CourseException;

	public List<String> getRecordsSuccessMessage();
	public List<String> getRecordsFailureMessage();
}
