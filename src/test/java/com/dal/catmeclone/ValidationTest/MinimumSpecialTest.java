package com.dal.catmeclone.ValidationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.MinimumSpecial;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumSpecialTest {

    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationPolicy checkspecial = abstractFactory.createValidationAbstractFactory().createMinimumSpecial();


	@Test
	void TestMinimumSpecial() throws Exception {
		User u = abstractFactory.createModelAbstractFactory().createUser();
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
