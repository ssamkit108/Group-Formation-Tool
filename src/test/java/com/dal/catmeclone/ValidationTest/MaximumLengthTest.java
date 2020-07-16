package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class MaximumLengthTest {
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy CheckMaximumLength = abstractFactoryTest.createValidationAbstractFactory().createMaximumLength();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void TestMaximumLength() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("Hello");
        CheckMaximumLength.setValue("6");
        Assert.assertTrue(CheckMaximumLength.isValid(u));
        u.setPassword("Hello");
        CheckMaximumLength.setValue("10");
        Assert.assertTrue(CheckMaximumLength.isValid(u));
    }
}
