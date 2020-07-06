package com.dal.catmeclone.adminTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.Course;

@SuppressWarnings("deprecation")
public class CourseTest {

    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	@Test
	public void setCourseNameTest() {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();

		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void getCourseNameTest() {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void setCourseIDTest() {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
	
	@Test
	public void getCourseIDTest() {
		Course c = abstractFactory.createModelAbstractFactory().crateCourse();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
  }
