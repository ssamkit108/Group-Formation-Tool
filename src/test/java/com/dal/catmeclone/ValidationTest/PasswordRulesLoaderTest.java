package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.PasswordRulesLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class PasswordRulesLoaderTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    PasswordRulesLoader passwordrules = abstractFactoryTest.createValidationAbstractFactory().createPasswordRulesLoader();

    @Test
    public void TestgetValidationRulesList() {
        assertTrue(passwordrules.getValidationRulesList() instanceof List<?>);
    }
}
