package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

class MinimumUpperCaseTest {

	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	ValidationPolicy checkupper = abstractFactoryTest.createValidationAbstractFactory().createMinimumUpper();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	@SuppressWarnings("deprecation")
	@Test
	public void TestMinimumUpper() throws Exception{
		User u = modelfact.createUser();
		u.setPassword("SaMkit");
		checkupper.setValue("2");
		Assert.isTrue(checkupper.isValid(u));
		u.setPassword("SaMkit");
		checkupper.setValue("3");
		Assert.isTrue(!checkupper.isValid(u));
		u.setPassword("SaMkit");
		checkupper.setValue("1");
		Assert.isTrue(checkupper.isValid(u));

	}

}
