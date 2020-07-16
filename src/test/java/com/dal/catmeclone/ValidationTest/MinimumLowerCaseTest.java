package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MinimumLowerCaseTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy checklower = abstractFactoryTest.createValidationAbstractFactory().createMinimumLower();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void TestMinimumUpper() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("SaMkit");
        checklower.setValue("2");
        Assert.assertTrue(checklower.isValid(u));
        u.setPassword("SAMKIT");
        checklower.setValue("3");
        Assert.assertTrue(!checklower.isValid(u));
        u.setPassword("SaMkit");
        checklower.setValue("1");
        Assert.assertTrue(checklower.isValid(u));
    }
}
