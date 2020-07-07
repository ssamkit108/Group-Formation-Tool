package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter	.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;


public class MaximumLengthTest {
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ValidationPolicy CheckMaximumLength = abstractFactoryTest.createValidationAbstractFactory().createMaximumLength();

	
	@Test
	public void TestMaximumLength() throws Exception {
		User u = new User();		
		u.setPassword("Hello");
		CheckMaximumLength.setValue("6");
		Assert.isTrue(CheckMaximumLength.isValid(u));
		u.setPassword("Hello");
		CheckMaximumLength.setValue("10");
		Assert.isTrue(CheckMaximumLength.isValid(u));
	
	}
}
