package com.dal.catmeclone.ValidationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import org.junit.jupiter.api.Test;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.PasswordRulesLoader;

public class PasswordRulesLoaderTest {

	AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();
	ValidationAbstractFactory validationAbstractFactory=abstractFactory.createValidationAbstractFactory();
	@Test
	public void TestgetValidationRulesList() {
		PasswordRulesLoader passwordrules=validationAbstractFactory.createPasswordRulesLoader();
		assertTrue(passwordrules.getValidationRulesList() instanceof List<?>);
	}
}
