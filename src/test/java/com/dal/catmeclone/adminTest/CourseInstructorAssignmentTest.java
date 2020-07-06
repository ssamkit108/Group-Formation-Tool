package com.dal.catmeclone.adminTest;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentTest {
	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		c.setCourseID(123);
		User u = abstractFactory.createModelAbstractFactory().createUser();
		u.setBannerId("B00833211");
		Role r = new Role();
		r.setRoleName("Instructor");
		CourseInstructorAssignmentMock mock= new CourseInstructorAssignmentMock();
		Assert.isTrue(mock.enrollInstructorForCourse(u,c,r));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkInstructorForCourseTest() throws UserDefinedSQLException, SQLException{
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		CourseInstructorAssignmentMock mock= new CourseInstructorAssignmentMock();
		c.setCourseID(123);
		c.setCourseName("sdc");
		Assert.isTrue(mock.checkInstructorForCourse(new Course(c.getCourseID(), c.getCourseName())));
	}
}
