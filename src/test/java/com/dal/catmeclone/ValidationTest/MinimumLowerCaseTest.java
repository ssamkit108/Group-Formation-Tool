package com.dal.catmeclone.ValidationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.MinimumLower;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumLowerCaseTest {


	 AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationPolicy checklower = abstractFactory.createValidationAbstractFactory().createMinimumLower();


	@SuppressWarnings("deprecation")
	@Test
	public void TestMinimumUpper() throws Exception {
		User u = abstractFactory.createModelAbstractFactory().createUser();		
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
