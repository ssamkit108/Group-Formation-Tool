package com.dal.catmeclone;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentTest {
	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course c = new Course();
		c.setCourseID(123);
		User u = new User();
		u.setBannerId("B00833211");
		Role r = new Role();
		r.setRoleName("Instructor");
		CourseInstructorAssignmentMock mock= new CourseInstructorAssignmentMock();
		Assert.isTrue(mock.enrollInstructorForCourse(u,c,r));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkInstructorForCourseTest() throws UserDefinedSQLException, SQLException{
		Course u = new Course();
		CourseInstructorAssignmentMock mock= new CourseInstructorAssignmentMock();
		u.setCourseID(123);
		u.setCourseName("sdc");
		Assert.isTrue(mock.checkInstructorForCourse(new Course(u.getCourseID(), u.getCourseName())));
	}
}
