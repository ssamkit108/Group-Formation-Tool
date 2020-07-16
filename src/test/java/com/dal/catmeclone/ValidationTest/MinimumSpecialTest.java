package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MinimumSpecialTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy checkspecial = abstractFactoryTest.createValidationAbstractFactory().createMinimumSpecial();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();

    @Test
    void TestMinimumSpecial() throws Exception {
        User u = modelAbstractFactory.createUser();
        u.setPassword("Samkit@108");
        checkspecial.setValue("2");
        Assert.assertTrue(!checkspecial.isValid(u));
        u.setPassword("Samk@@it@108");
        checkspecial.setValue("2");
        Assert.assertTrue(checkspecial.isValid(u));
        u.setPassword("Samkit@108");
        checkspecial.setValue("1");
        Assert.assertTrue(checkspecial.isValid(u));
        u.setPassword("Samkit1108");
        checkspecial.setValue("2");
        Assert.assertTrue(!checkspecial.isValid(u));

    }

}
