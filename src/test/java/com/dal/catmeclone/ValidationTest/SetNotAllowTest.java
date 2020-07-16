package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SetNotAllowTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationPolicy checknotallow = abstractFactoryTest.createValidationAbstractFactory().createSetNotAllow();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @Test
    void TestSetNotAllow() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("Sam");
        checknotallow.setValue("abc");
        Assert.assertTrue(!checknotallow.isValid(u));
        u.setPassword("xyz");
        checknotallow.setValue("abc");
        Assert.assertTrue(checknotallow.isValid(u));
    }

}
