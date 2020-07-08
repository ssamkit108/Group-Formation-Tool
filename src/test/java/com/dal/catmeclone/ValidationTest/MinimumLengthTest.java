package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

public class MinimumLengthTest {
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ValidationPolicy CheckMinimumLength = abstractFactoryTest.createValidationAbstractFactory().createMinimumLength();


	@Test
	public void TestMinimumLength() throws Exception {
		User u = new User();
		u.setPassword("Hello");
		CheckMinimumLength.setValue("1");
		Assert.isTrue(CheckMinimumLength.isValid(u));
		u.setPassword("Hello");
		CheckMinimumLength.setValue("10");
		Assert.isTrue(!CheckMinimumLength.isValid(u));
	
	}
}
