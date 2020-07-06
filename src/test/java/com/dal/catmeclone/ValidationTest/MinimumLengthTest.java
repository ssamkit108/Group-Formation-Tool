package com.dal.catmeclone.ValidationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.MinimumLength;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

public class MinimumLengthTest {
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationPolicy CheckMinimumLength = abstractFactory.createValidationAbstractFactory().createMinimumLength();


	@Test
	public void TestMinimumLength() throws Exception {
		User u = abstractFactory.createModelAbstractFactory().createUser();
		u.setPassword("Hello");
		CheckMinimumLength.setValue("1");
		Assert.isTrue(CheckMinimumLength.isValid(u));
		u.setPassword("Hello");
		CheckMinimumLength.setValue("10");
		Assert.isTrue(!CheckMinimumLength.isValid(u));
	
	}
}
