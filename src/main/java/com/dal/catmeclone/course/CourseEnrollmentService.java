package com.dal.catmeclone.course;

import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseEnrollmentService {

    public boolean enrollStudentForCourse(MultipartFile file, Course course) throws FileRelatedException, UserDefinedException;

    public boolean enrollTAForCourse(User Ta, Course course);

    public List<Course> getCourseEnrolledForUser(User user) throws UserDefinedException;

    public Role getUserRoleForCourse(User user, Course course) throws UserDefinedException;

    public List<String> getRecordsSuccessMessage();

    public List<String> getRecordsFailureMessage();
}
