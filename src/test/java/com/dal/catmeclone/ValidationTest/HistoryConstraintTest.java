package com.dal.catmeclone.ValidationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.Validation.HistoryConstraintDao;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class HistoryConstraintTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ValidationAbstractFactory validationFactory = abstractFactoryTest.createValidationAbstractFactory();
    HistoryConstraintDao historyConstraintMock = new HistoryConstraintMock();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();

    @Test
    public void TestHistoryConstraint() throws Exception {
        User user = modelFactory.createUser();
        //Valid
        user.setPassword("Hello");
        Assert.assertFalse(historyConstraintMock.fetchPasswordList(user, 2).size() == 2);

        //Invalid
        user.setPassword("Password@123");
        Assert.assertTrue(!(historyConstraintMock.fetchPasswordList(user, 2).size() == 2));
    }
}
