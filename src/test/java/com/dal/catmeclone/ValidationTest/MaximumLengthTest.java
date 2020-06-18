package com.dal.catmeclone.ValidationTest;

import java.sql.SQLException;

import org.junit.jupiter	.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.Validation.MaximumLength;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.User;


public class MaximumLengthTest {
	ValidationPolicy CheckMaximumLength =new MaximumLength();
	
	@Test
	public void TestMaximumLength() throws Exception {
		User u=new User();
		
		u.setPassword("Hello");
		CheckMaximumLength.setValue("6");
		Assert.isTrue(CheckMaximumLength.isValid(u));
		u.setPassword("Hello");
		CheckMaximumLength.setValue("10");
		Assert.isTrue(CheckMaximumLength.isValid(u));
	
	}
}
