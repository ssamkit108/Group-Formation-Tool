package com.dal.catmeclone.ValidationTest;

import java.sql.SQLException;

import org.junit.jupiter	.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.MaximumLength;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


public class MaximumLengthTest {
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationPolicy CheckMaximumLength = abstractFactory.createValidationAbstractFactory().createMaximumLength();

	
	@Test
	public void TestMaximumLength() throws Exception {
		User u = abstractFactory.createModelAbstractFactory().createUser();		
		u.setPassword("Hello");
		CheckMaximumLength.setValue("6");
		Assert.isTrue(CheckMaximumLength.isValid(u));
		u.setPassword("Hello");
		CheckMaximumLength.setValue("10");
		Assert.isTrue(CheckMaximumLength.isValid(u));
	
	}
}
