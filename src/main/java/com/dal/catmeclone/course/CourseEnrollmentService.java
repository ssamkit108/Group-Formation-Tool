package com.dal.catmeclone.course;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public interface CourseEnrollmentService {

	public boolean enrollStudentForCourse(MultipartFile file, Course course) throws FileRelatedException;

	public boolean enrollTAForCourse(User Ta, Course course);

	public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedSQLException;

	public Role getUserRoleForCourse(User user, Course course) throws UserDefinedSQLException;

	public List<String> getRecordsSuccessMessage();

	public List<String> getRecordsFailureMessage();
}
