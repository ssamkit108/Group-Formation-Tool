package com.dal.catmeclone.coursesTest;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public class CourseServiceTest {
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	@Test
	void getallcoursesbyuserTest() {
		User user = abstractFactory.createModelAbstractFactory().createUser();
		user.setBannerId("B00839818");
		ArrayList<Course> courselst = new ArrayList<Course>();
		CourseDaoMock courseDBmock = new CourseDaoMock();
		courselst = courseDBmock.getallcoursesbyuser(user);
		assertNotNull(courselst);
	}

	@Test
	public void getallcoursesTest() {
		CourseDaoMock courseDBmock = new CourseDaoMock();
		assertNotNull(courseDBmock.getallcourses());
	}

}
