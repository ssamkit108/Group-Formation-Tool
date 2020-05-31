package com.dal.catmeclone;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;

public class AdminAddCourseTest {
	
	@SuppressWarnings("deprecation")
	@Test
	public void insertCourseTest() throws UserDefinedSQLException, SQLException {
		Course u = new Course();
		CourseMock mock= new CourseMock();
		u.setCourseID(123);
		u.setCourseName("sdc");
		Assert.isTrue(mock.insertCourse(u));
	}

}
