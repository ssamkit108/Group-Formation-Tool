package com.dal.catmeclone.ValidationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.SetNotAllow;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class SetNotAllowTest {
	
	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

	ValidationPolicy checknotallow= abstractFactory.createValidationAbstractFactory().createSetNotAllow();
	
	@Test
	void TestSetNotAllow() throws Exception {
		User u = abstractFactory.createModelAbstractFactory().createUser();
		u.setPassword("Sam");
		checknotallow.setValue("abc");
		Assert.isTrue(!checknotallow.isValid(u));
		u.setPassword("xyz");
		checknotallow.setValue("abc");
		Assert.isTrue(checknotallow.isValid(u));
	}

}
