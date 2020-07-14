package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter	.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;


public class MaximumLengthTest {
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	ValidationPolicy CheckMaximumLength = abstractFactoryTest.createValidationAbstractFactory().createMaximumLength();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

	
	@Test
	public void TestMaximumLength() throws Exception {
		User u = modelfact.createUser();		
		u.setPassword("Hello");
		CheckMaximumLength.setValue("6");
		Assert.isTrue(CheckMaximumLength.isValid(u));
		u.setPassword("Hello");
		CheckMaximumLength.setValue("10");
		Assert.isTrue(CheckMaximumLength.isValid(u));
	
	}
}
