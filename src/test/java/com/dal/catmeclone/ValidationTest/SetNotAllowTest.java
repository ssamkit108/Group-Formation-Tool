package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.Validation.ValidationPolicy;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class SetNotAllowTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();

    ValidationPolicy checknotallow = abstractFactoryTest.createValidationAbstractFactory().createSetNotAllow();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

    @Test
    void TestSetNotAllow() throws Exception {
        User u = modelfact.createUser();
        u.setPassword("Sam");
        checknotallow.setValue("abc");
        Assert.isTrue(!checknotallow.isValid(u));
        u.setPassword("xyz");
        checknotallow.setValue("abc");
        Assert.isTrue(checknotallow.isValid(u));
    }

}
