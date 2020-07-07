package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumSpecialTest {

	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ValidationPolicy checkspecial = abstractFactoryTest.createValidationAbstractFactory().createMinimumSpecial();


	@Test
	void TestMinimumSpecial() throws Exception {
		User u = new User();
		u.setPassword("Samkit@108");
		checkspecial.setValue("2");
		Assert.isTrue(!checkspecial.isValid(u));
		u.setPassword("Samk@@it@108");
		checkspecial.setValue("2");
		Assert.isTrue(checkspecial.isValid(u));
		u.setPassword("Samkit@108");
		checkspecial.setValue("1");
		Assert.isTrue(checkspecial.isValid(u));
		u.setPassword("Samkit1108");
		checkspecial.setValue("2");
		Assert.isTrue(!checkspecial.isValid(u));

	}

}
