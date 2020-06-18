package com.dal.catmeclone.coursesTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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

}
