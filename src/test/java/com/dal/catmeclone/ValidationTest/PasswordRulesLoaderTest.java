package com.dal.catmeclone.ValidationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.PasswordRulesLoader;

public class PasswordRulesLoaderTest {

	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	PasswordRulesLoader passwordrules=abstractFactoryTest.createValidationAbstractFactory().createPasswordRulesLoader();
	@Test
	public void TestgetValidationRulesList() {
		assertTrue(passwordrules.getValidationRulesList() instanceof List<?>);
	}
}
