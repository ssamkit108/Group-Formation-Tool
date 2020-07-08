package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class SetNotAllowTest {
	
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();

	ValidationPolicy checknotallow= abstractFactoryTest.createValidationAbstractFactory().createSetNotAllow();
	
	@Test
	void TestSetNotAllow() throws Exception {
		User u = new User();
		u.setPassword("Sam");
		checknotallow.setValue("abc");
		Assert.isTrue(!checknotallow.isValid(u));
		u.setPassword("xyz");
		checknotallow.setValue("abc");
		Assert.isTrue(checknotallow.isValid(u));
	}

}
