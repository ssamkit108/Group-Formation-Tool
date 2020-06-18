package com.dal.catmeclone.coursesTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class CourseDaoTest {

	@Test
	public void getCourseforValidCourseID() {
		int courseid = 100;
		CourseDaoMock mock = new CourseDaoMock();
		Assert.notNull(mock.getCourse(courseid), "Passed: Course Exist with given course id");
	}

	@Test
	public void getNullForNonExistingCourseId() {
		int courseid = 201;
		CourseDaoMock mock = new CourseDaoMock();
		Assert.isNull(mock.getCourse(courseid), "Passed: Course Does not Exist with given course id");
	}
	
	@Test
	public void getCoursebyuser() {
		String bannerid = "B00839818";
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course(101, "Advance topic in web"));
		courseList.add(new Course(100,"c"));
		User u = new User();
		u.setBannerId(bannerid);	
		CourseDaoMock mock = new CourseDaoMock();
		assertTrue(courseList.size()== mock.getallcoursesbyuser(u).size());
		
		bannerid = "B00123456";
		u = new User();
		u.setBannerId(bannerid);
		assertTrue(mock.getallcoursesbyuser(u) == null);
		
	}
	
	@Test
	public void getallcourses() {
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course(101, "Advance topic in web"));
		courseList.add(new Course(100,"Advance Topic in SDC"));
		CourseDaoMock mock = new CourseDaoMock();
		assertTrue(courseList.size()== mock.getallcourses().size());
		
	}
	
	@Test
	public void getcourse() {
		int courseid = 100;
		String coursename = "Advance Topic in SDC";
		Course c = new Course();
		c.setCourseID(courseid);
		c.setCourseName(coursename);
		CourseDaoMock mock = new CourseDaoMock();
		assertFalse(mock.getCourse(100).getCourseName().isEmpty());
		assertTrue(mock.getCourse(100).getCourseName().equals(c.getCourseName()));
	}

}
