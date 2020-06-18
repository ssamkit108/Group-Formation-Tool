package com.dal.catmeclone.courses;

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
		ArrayList<Course> crlst = new ArrayList<Course>();
		CourseDaoMock courseDBmock = new CourseDaoMock();
		crlst = courseDBmock.getallcoursesbyuser(user);
		assertNotNull(crlst);
	}

	@Test
	public void getallcoursesTest() {
		
		CourseDaoMock courseDBmock = new CourseDaoMock();
		assertNotNull(courseDBmock.getallcourses());
	}

}
