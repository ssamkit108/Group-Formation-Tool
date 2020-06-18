package com.dal.catmeclone.ValidationTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import com.dal.catmeclone.Validation.MinimumUpper;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;

class MinimumUpperCaseTest {

	ValidationPolicy checkupper = new MinimumUpper();

	@SuppressWarnings("deprecation")
	@Test
	public void TestMinimumUpper() throws Exception {

		User u = new User();
		u.setPassword("SaMkit");
		checkupper.setValue("2");
		Assert.isTrue(checkupper.isValid(u));
		u.setPassword("SaMkit");
		checkupper.setValue("3");
		Assert.isTrue(!checkupper.isValid(u));
		u.setPassword("SaMkit");
		checkupper.setValue("1");
		Assert.isTrue(checkupper.isValid(u));

	}

}
