package com.dal.catmeclone.adminTest;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public class CourseManagementTest {

    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		CourseMock mock= new CourseMock();
		c.setCourseID(123);
		c.setCourseName("sdc");
		Assert.isTrue(mock.insertCourse(c));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deleteCourseTest() throws UserDefinedSQLException, SQLException{
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		CourseMock mock = new CourseMock();
		c.setCourseID(123);
		Assert.isTrue(mock.deleteCourse(c.getCourseID()));
	};
	
	@Test
	public void getAllCoursesTest() throws UserDefinedSQLException, SQLException{
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		CourseMock mock = new CourseMock();
		c.setCourseID(123);
		c.setCourseName("sdc");
		List<Course> courses = new ArrayList<Course>();
		courses.add(c);
		assertEquals(mock.getAllCourses(), courses);
	};	
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkCourseExists() throws UserDefinedSQLException, SQLException{
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		CourseMock mock = new CourseMock();
		c.setCourseID(123);
		c.setCourseName("sdc");
		Assert.isTrue(mock.checkCourseExists(new Course(c.getCourseID(), c.getCourseName())));
	}
	
}
