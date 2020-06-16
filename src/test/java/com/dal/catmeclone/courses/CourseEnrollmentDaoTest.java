package com.dal.catmeclone.courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class CourseEnrollmentDaoTest {
	
	CourseEnrollmentDaoMock mock;
	@BeforeEach
	public void set()
	{
		mock=new CourseEnrollmentDaoMock();
	}
	
	@Test
	public void enrollUserForCourseNonExisting()
	{
		
		Course course = new Course(100, "CourseA");
		User student = new User("B00000007");
		Role role = new Role("Student");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User Enrolled Successfully");
	}
	
	@Test
	public void enrollUserForCourseExisting()
	{
		
		Course course = new Course(100, "CourseA");
		User student = new User("B00000001");
		Role role = new Role("Student");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User already exist");
	}
	
	@Test
	public void hasEnrolledInCourseTrue()
	{
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(mock.hasEnrolledInCourse("B00000001",100 ), "Passed: Course Exist with given course id");
	}
	
	@Test
	public void hasEnrolledInCourseFalse()
	{
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(!mock.hasEnrolledInCourse("B00000009",100 ), "Passed: Course Exist with given course id");
	}
	
	@Test
	public void getAllEnrolledCourse()
	{
		User user = new User("B00000001");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.notEmpty(mock.getAllEnrolledCourse(user), "Passed: User have enrolled courses");
	}
	
	@Test
	public void getAllEnrolledCourseNoCourseEnrolled()
	{
		User user = new User("B00000001");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(mock.getAllEnrolledCourse(user).size()!=0, "Passed: User don't any have enrolled courses");
	}
	
	@Test
	public void getUserRoleForCourse()
	{
		User user = new User("B00000001");
		Course course = new Course(100, "CourseA");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.notNull(mock.getUserRoleForCourse(user, course), "Passed: User have a role tagged to the course");
	}
	
	
	
	@Test
	public void getUserRoleForCourseUserNotEnrolledInCourse()
	{
		User user = new User("B00000009");
		Course course = new Course(100, "CourseA");
		//CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isNull(mock.getUserRoleForCourse(user, course), "Passed: User don't have any role tagged to the course");
	}


}
