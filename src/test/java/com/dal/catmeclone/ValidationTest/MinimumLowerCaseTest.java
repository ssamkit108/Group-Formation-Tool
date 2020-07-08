package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumLowerCaseTest {


	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ValidationPolicy checklower = abstractFactoryTest.createValidationAbstractFactory().createMinimumLower();


	@SuppressWarnings("deprecation")
	@Test
	public void TestMinimumUpper() throws Exception {
		User u = new User();		
		u.setPassword("SaMkit");
		checklower.setValue("2");
		Assert.isTrue(checklower.isValid(u));
		u.setPassword("SAMKIT");
		checklower.setValue("3");
		Assert.isTrue(!checklower.isValid(u));
		u.setPassword("SaMkit");
		checklower.setValue("1");
		Assert.isTrue(checklower.isValid(u));
	}
}
