package com.dal.catmeclone;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public class AdminDashboardTest {

	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course u = new Course();
		CourseMock mock= new CourseMock();
		u.setCourseID(123);
		u.setCourseName("sdc");
		Assert.isTrue(mock.insertCourse(u));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deleteCourseTest() throws UserDefinedSQLException, SQLException{
		Course u = new Course();
		CourseMock mock = new CourseMock();
		u.setCourseID(123);
		Assert.isTrue(mock.deleteCourse(u.getCourseID()));
	};
	
	@Test
	public void getAllCoursesTest() throws UserDefinedSQLException, SQLException{
		Course u = new Course();
		CourseMock mock = new CourseMock();
		u.setCourseID(123);
		u.setCourseName("sdc");
		List<Course> c = new ArrayList<Course>();
		c.add(u);
		assertEquals(mock.getAllCourses(), c);
	};	
	
}
