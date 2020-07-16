package com.dal.catmeclone.coursesTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.course.CourseAbstractFactory;
import com.dal.catmeclone.course.CourseService;
import com.dal.catmeclone.course.CoursesDao;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CourseServiceTest {
	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
	CourseAbstractFactory courseAbstractFactoryTest = abstractFactoryTest.createCourseAbstractFactory();
	CoursesDao courseDaoMock;
	CourseService courseService;

	@BeforeEach
	void setup() {
		courseDaoMock = courseAbstractFactoryTest.createCourseDao();
		courseService = courseAbstractFactoryTest.createCourseService(courseDaoMock);
	}

	@Test
	public void getCourseforValidCourseID() throws UserDefinedException {
		int courseid = 100;
		courseDaoMock = courseAbstractFactoryTest.createCourseDao();
		courseService = courseAbstractFactoryTest.createCourseService(courseDaoMock);
		Assert.notNull(courseService.getCourse(courseid), "Passed: Course Exist with given course id");
	}

	@Test
	public void getNullForNonExistingCourseId() throws UserDefinedException {
		int courseid = 201;
		courseDaoMock = courseAbstractFactoryTest.createCourseDao();
		courseService = courseAbstractFactoryTest.createCourseService(courseDaoMock);
		Assert.isNull(courseService.getCourse(courseid), "Passed: Course Does not Exist with given course id");
	}

	@Test
	public void getallcourses() throws SQLException, UserDefinedException {
		courseDaoMock = courseAbstractFactoryTest.createCourseDao();
		courseService = courseAbstractFactoryTest.createCourseService(courseDaoMock);
		assertTrue(courseService.getallcourses().size() == 2);
	}

	@Test
	public void getcourse() throws UserDefinedException {
		courseDaoMock = courseAbstractFactoryTest.createCourseDao();
		courseService = courseAbstractFactoryTest.createCourseService(courseDaoMock);
		
		int courseid = 100;
		String coursename = "Advance Topic in SDC";
		Course course = modelFactory.createCourse();
		course.setCourseID(courseid);
		course.setCourseName(coursename);
		assertFalse(courseService.getCourse(100).getCourseName().isEmpty());
		assertTrue(courseService.getCourse(100).getCourseName().equals(course.getCourseName()));
	}

}
