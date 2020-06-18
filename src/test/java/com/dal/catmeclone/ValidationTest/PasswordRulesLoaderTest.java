package com.dal.catmeclone.ValidationTest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.Validation.PasswordRulesLoader;

public class PasswordRulesLoaderTest {
	
	@Test
	public void TestgetValidationRulesList() {
		PasswordRulesLoader passwordrules=SystemConfig.instance().getPasswordRules();
		assertTrue(passwordrules.getValidationRulesList() instanceof List<?>);
	}
}
