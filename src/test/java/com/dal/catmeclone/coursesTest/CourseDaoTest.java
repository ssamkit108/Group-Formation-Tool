package com.dal.catmeclone.coursesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class CourseDaoTest {
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();


	@Test
	public void getCourseforValidCourseID() throws UserDefinedSQLException, CourseException {
		int courseid = 100;
		CoursesDao mock = abstractFactoryTest.createCourseAbstractFactory().createCoursesDao();
		Assert.notNull(mock.getCourse(courseid), "Passed: Course Exist with given course id");
	}

	@Test
	public void getNullForNonExistingCourseId() throws UserDefinedSQLException, CourseException {
		int courseid = 201;
		CoursesDao mock = abstractFactoryTest.createCourseAbstractFactory().createCoursesDao();
		Assert.isNull(mock.getCourse(courseid), "Passed: Course Does not Exist with given course id");
	}
	
	@Test
	public void getCoursebyuser() throws UserDefinedSQLException {
		String bannerid = "B00839818";
		ArrayList<Course> courseList = new ArrayList<Course>();
		Course c = new Course();
		c.setCourseID(101);
		c.setCourseName("Advance topic in web");
		courseList.add(c);

		Course c2 = new Course();
		c2.setCourseID(100);
		c2.setCourseName("sdc");
		courseList.add(c2);
		
		User u = new User();
		u.setBannerId(bannerid);
		
		CoursesDao mock = abstractFactoryTest.createCourseAbstractFactory().createCoursesDao();
		assertTrue(courseList.size()== mock.getallcoursesbyuser(u).size());
		
		bannerid = "B00123456";
		u = new User();
		u.setBannerId(bannerid);
		assertTrue(mock.getallcoursesbyuser(u) == null);
		
	}
	
	@Test
	public void getallcourses() throws SQLException, UserDefinedSQLException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		Course c = new Course();
		c.setCourseID(101);
		c.setCourseName("Advance topic in web");
		courseList.add(c);

		Course c2 = new Course();
		c2.setCourseID(100);
		c2.setCourseName("Advance Topic in SDC");
		courseList.add(c2);

		CoursesDao mock = abstractFactoryTest.createCourseAbstractFactory().createCoursesDao();
		assertTrue(courseList.size()== mock.getallcourses().size());
		
	}
	
	@Test
	public void getcourse() throws UserDefinedSQLException, CourseException {
		int courseid = 100;
		String coursename = "Advance Topic in SDC";
		Course c = new Course();
		c.setCourseID(courseid);
		c.setCourseName(coursename);
		CoursesDao mock = abstractFactoryTest.createCourseAbstractFactory().createCoursesDao();
		assertFalse(mock.getCourse(100).getCourseName().isEmpty());
		assertTrue(mock.getCourse(100).getCourseName().equals(c.getCourseName()));
	}

}
