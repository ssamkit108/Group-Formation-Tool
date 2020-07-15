package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class MinimumLowerCaseTest {


    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    ValidationPolicy checklower = abstractFactoryTest.createValidationAbstractFactory().createMinimumLower();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    @SuppressWarnings("deprecation")
    @Test
    public void TestMinimumUpper() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("SaMkit");
        checklower.setValue("2");
        Assert.isTrue(checklower.isValid(u));
        u.setPassword("SAMKIT");
        checklower.setValue("3");
        Assert.isTrue(!checklower.isValid(u));
        u.setPassword("SaMkit");
        checklower.setValue("1");
        Assert.isTrue(checklower.isValid(u));
    }
}
