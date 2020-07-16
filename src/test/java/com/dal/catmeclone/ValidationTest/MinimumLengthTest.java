package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MinimumLengthTest {
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy CheckMinimumLength = abstractFactoryTest.createValidationAbstractFactory().createMinimumLength();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void TestMinimumLength() throws Exception {
        User u = modelAbstractFactory.createUser();
        u.setPassword("Hello");
        CheckMinimumLength.setValue("1");
        Assert.assertTrue(CheckMinimumLength.isValid(u));
        u.setPassword("Hello");
        CheckMinimumLength.setValue("10");
        Assert.assertTrue(!CheckMinimumLength.isValid(u));

    }
}
