package com.dal.catmeclone.admin;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.model.Course;

@SuppressWarnings("deprecation")
public class CourseTest {

	@Test
	public void setCourseNameTest() {
		Course c = new Course();
		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void getCourseNameTest() {
		Course c = new Course();
		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void setCourseIDTest() {
		Course c = new Course();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
	
	@Test
	public void getCourseIDTest() {
		Course c = new Course();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
  }
