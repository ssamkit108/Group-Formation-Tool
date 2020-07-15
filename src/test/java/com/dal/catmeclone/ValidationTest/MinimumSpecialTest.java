package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class MinimumSpecialTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    ValidationPolicy checkspecial = abstractFactoryTest.createValidationAbstractFactory().createMinimumSpecial();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    @Test
    void TestMinimumSpecial() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("Samkit@108");
        checkspecial.setValue("2");
        Assert.isTrue(!checkspecial.isValid(u));
        u.setPassword("Samk@@it@108");
        checkspecial.setValue("2");
        Assert.isTrue(checkspecial.isValid(u));
        u.setPassword("Samkit@108");
        checkspecial.setValue("1");
        Assert.isTrue(checkspecial.isValid(u));
        u.setPassword("Samkit1108");
        checkspecial.setValue("2");
        Assert.isTrue(!checkspecial.isValid(u));

    }

}
