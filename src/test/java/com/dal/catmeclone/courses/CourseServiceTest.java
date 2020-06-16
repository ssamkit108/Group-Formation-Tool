package com.dal.catmeclone.courses;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.dal.catmeclone.course.CourseService;
import com.dal.catmeclone.exceptionhandler.CourseException;
import com.dal.catmeclone.exceptionhandler.FileRelatedException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.User;

public class CourseServiceTest {

	@Test
	void getallcoursesbyuserTest() {
		User user = new User();
		user.setBannerId("B00839818");
		
		ArrayList<Course> crlst = new ArrayList<Course>();
		CourseDaoMock courseDBmock=new CourseDaoMock();	
		crlst = courseDBmock.getallcoursesbyuser(user);
		assertNotNull(crlst);
	}
	
	

	@Test
	public void getallcoursesTest() {
		// TODO Auto-generated method stub
		CourseDaoMock courseDBmock=new CourseDaoMock();
		
		ArrayList<Course> crlst = new ArrayList<Course>();
	
		assertNotNull(courseDBmock.getallcourses());
	}

	
	
}
