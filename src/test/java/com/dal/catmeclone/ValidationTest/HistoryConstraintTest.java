package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

public class HistoryConstraintTest {
	
	
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	HistoryConstraintMock HistoryConstraintMock = abstractFactoryTest.createValidationAbstractFactory().createHistoryConstraintMock();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


	@Test
	public void TestHistoryConstraint() throws Exception {
		User u = modelfact.createUser();	
		//Valid
		u.setPassword("Hello");
		HistoryConstraintMock.setValue("3");
		Assert.isTrue(HistoryConstraintMock.isValid(u));
		
		//Invalid
		u.setPassword("Password@123");
		HistoryConstraintMock.setValue("2");
		Assert.isTrue(!HistoryConstraintMock.isValid(u));
	
	}
}
