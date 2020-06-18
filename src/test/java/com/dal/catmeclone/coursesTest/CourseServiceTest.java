package com.dal.catmeclone.coursesTest;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

public class CourseServiceTest {

	@Test
	void getallcoursesbyuserTest() {
		User user = new User();
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
