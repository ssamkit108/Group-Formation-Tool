package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

public class MinimumLengthTest {
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	ValidationPolicy CheckMinimumLength = abstractFactoryTest.createValidationAbstractFactory().createMinimumLength();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	@Test
	public void TestMinimumLength() throws Exception {
		User u = modelfact.createUser();
		u.setPassword("Hello");
		CheckMinimumLength.setValue("1");
		Assert.isTrue(CheckMinimumLength.isValid(u));
		u.setPassword("Hello");
		CheckMinimumLength.setValue("10");
		Assert.isTrue(!CheckMinimumLength.isValid(u));
	
	}
}
