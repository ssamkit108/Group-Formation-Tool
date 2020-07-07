package com.dal.catmeclone.adminTest;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.admin.CourseInstructorAssignmentDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseInstructorAssignmentTest {
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();

	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course c = new Course();
		c.setCourseID(123);
		User u = new User();
		u.setBannerId("B00833211");
		Role r = new Role();
		r.setRoleName("Instructor");

		CourseInstructorAssignmentDao mock = abstractFactoryTest.createAdminAbstractFactory()
				.createCourseInstructorAssignmentDao();
		Assert.isTrue(mock.enrollInstructorForCourse(u, c, r));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkInstructorForCourseTest() throws UserDefinedSQLException, SQLException {
		Course c = new Course();
		CourseInstructorAssignmentDao mock = abstractFactoryTest.createAdminAbstractFactory()
				.createCourseInstructorAssignmentDao();
		c.setCourseID(123);
		c.setCourseName("sdc");
		Assert.isTrue(mock.checkInstructorForCourse(new Course(c.getCourseID(), c.getCourseName())));
	}
}
