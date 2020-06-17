package com.dal.catmeclone.ValidationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.dal.catmeclone.Validation.MinimumLower;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumLowerCaseTest {


	 ValidationPolicy checklower=new MinimumLower();

	@SuppressWarnings("deprecation")
	@Test
	public void TestMinimumUpper() throws Exception {
		
		User u=new User();
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
