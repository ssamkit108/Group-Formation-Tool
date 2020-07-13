package com.dal.catmeclone.adminTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

@SuppressWarnings("deprecation")
public class CourseTest {

	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

	@Test
	public void setCourseNameTest() {
		Course c = modelfact.createCourse();

		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void getCourseNameTest() {
		Course c = modelfact.createCourse();
		c.setCourseName("sdc");
		Assert.isTrue(c.getCourseName().equals("sdc"));
	}
	
	@Test
	public void setCourseIDTest() {
		Course c = modelfact.createCourse();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
	
	@Test
	public void getCourseIDTest() {
		Course c = modelfact.createCourse();
		c.setCourseID(5408);
		Assert.isTrue(c.getCourseID() == 5408);
	}
  }
