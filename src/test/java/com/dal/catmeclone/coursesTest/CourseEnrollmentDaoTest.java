package com.dal.catmeclone.coursesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class CourseEnrollmentDaoTest {

    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	CourseEnrollmentDaoMock mock;

	@BeforeEach
	public void set() {
		mock = new CourseEnrollmentDaoMock();
	}

	@Test
	public void enrollUserForCourseNonExisting() {


		Course course = abstractFactory.createModelAbstractFactory().crateCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		
		User student = abstractFactory.createModelAbstractFactory().createUser();
		student.setBannerId("B00000007");
		
		Role role = abstractFactory.createModelAbstractFactory().createRole();
		role.setRoleName("Student");
		// CourseEnrollmentDaoMock mock=new CourseEnrollmentDaoMock();
		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User Enrolled Successfully");
	}

	@Test
	public void enrollUserForCourseExisting() {

		Course course = abstractFactory.createModelAbstractFactory().crateCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		
		User student = abstractFactory.createModelAbstractFactory().createUser();
		student.setBannerId("B00000001");
		
		Role role = abstractFactory.createModelAbstractFactory().createRole();
		role.setRoleName("Student");

		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User already exist");
	}

	@Test
	public void hasEnrolledInCourseTrue() {
		Assert.isTrue(mock.hasEnrolledInCourse("B00000001", 100), "Passed: Course Exist with given course id");
	}

	@Test
	public void hasEnrolledInCourseFalse() {
		Assert.isTrue(!mock.hasEnrolledInCourse("B00000009", 100), "Passed: Course Exist with given course id");
	}

	@Test
	public void getAllEnrolledCourse() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		user.setBannerId("B00000001");
		Assert.notEmpty(mock.getAllEnrolledCourse(user), "Passed: User have enrolled courses");
	}

	@Test
	public void getAllEnrolledCourseNoCourseEnrolled() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		user.setBannerId("B00000001");		Assert.isTrue(mock.getAllEnrolledCourse(user).size() != 0, "Passed: User don't any have enrolled courses");
	}

	@Test
	public void getUserRoleForCourse() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		user.setBannerId("B00000001");
		Course course = abstractFactory.createModelAbstractFactory().crateCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		Assert.notNull(mock.getUserRoleForCourse(user, course), "Passed: User have a role tagged to the course");
	}

	@Test
	public void getUserRoleForCourseUserNotEnrolledInCourse() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		user.setBannerId("B00000009");
		Course course = abstractFactory.createModelAbstractFactory().crateCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		Assert.isNull(mock.getUserRoleForCourse(user, course), "Passed: User don't have any role tagged to the course");
	}

}
