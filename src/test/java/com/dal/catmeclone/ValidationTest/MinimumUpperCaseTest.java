package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MinimumUpperCaseTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy checkupper = abstractFactoryTest.createValidationAbstractFactory().createMinimumUpper();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void TestMinimumUpper() throws Exception {
        User u = modelAbstractFactory.createUser();
        u.setPassword("SaMkit");
        checkupper.setValue("2");
        Assert.assertTrue(checkupper.isValid(u));
        u.setPassword("SaMkit");
        checkupper.setValue("3");
        Assert.assertTrue(!checkupper.isValid(u));
        u.setPassword("SaMkit");
        checkupper.setValue("1");
        Assert.assertTrue(checkupper.isValid(u));

    }

}
