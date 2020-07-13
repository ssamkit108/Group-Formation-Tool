package com.dal.catmeclone.coursesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.course.CourseEnrollmentDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SpringBootTest
public class CourseEnrollmentDaoTest {

	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

	CourseEnrollmentDao mock;

	@BeforeEach
	public void set() {
		mock = abstractFactoryTest.createCourseAbstractFactory().createCourseEnrollmentDao();
	}

	@Test
	public void enrollUserForCourseNonExisting() throws UserDefinedSQLException {


		Course course = modelfact.createCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		
		User student = modelfact.createUser();
		student.setBannerId("B00000007");
		
		Role role = modelfact.createRole();
		role.setRoleName("Student");
		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User Enrolled Successfully");
	}

	@Test
	public void enrollUserForCourseExisting() throws UserDefinedSQLException {

		Course course = modelfact.createCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		
		User student = modelfact.createUser();
		student.setBannerId("B00000001");
		
		Role role = modelfact.createRole();
		role.setRoleName("Student");

		Assert.isTrue(mock.enrollUserForCourse(student, course, role), "Passed: User already exist");
	}

	@Test
	public void hasEnrolledInCourseTrue() throws UserDefinedSQLException {
		Assert.isTrue(mock.hasEnrolledInCourse("B00000001", 100), "Passed: Course Exist with given course id");
	}

	@Test
	public void hasEnrolledInCourseFalse() throws UserDefinedSQLException {
		Assert.isTrue(!mock.hasEnrolledInCourse("B00000009", 100), "Passed: Course Exist with given course id");
	}

	@Test
	public void getAllEnrolledCourse() throws UserDefinedSQLException {
		User user = modelfact.createUser();
		user.setBannerId("B00000001");
		Assert.notEmpty(mock.getAllEnrolledCourse(user), "Passed: User have enrolled courses");
	}

	@Test
	public void getAllEnrolledCourseNoCourseEnrolled() throws UserDefinedSQLException {
		User user = modelfact.createUser();
		user.setBannerId("B00000001");		
		Assert.isTrue(mock.getAllEnrolledCourse(user).size() != 0, "Passed: User don't any have enrolled courses");
	}

	@Test
	public void getUserRoleForCourse() throws UserDefinedSQLException {
		User user = modelfact.createUser();
		user.setBannerId("B00000001");
		Course course = modelfact.createCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		Assert.notNull(mock.getUserRoleForCourse(user, course), "Passed: User have a role tagged to the course");
	}

	@Test
	public void getUserRoleForCourseUserNotEnrolledInCourse() throws UserDefinedSQLException {
		User user = modelfact.createUser();
		user.setBannerId("B00000009");
		Course course = modelfact.createCourse();
		course.setCourseID(100);
		course.setCourseName("CourseA");
		Assert.isNull(mock.getUserRoleForCourse(user, course), "Passed: User don't have any role tagged to the course");
	}

}
