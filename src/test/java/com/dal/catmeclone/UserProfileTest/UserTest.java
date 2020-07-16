package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();


    @Test
    public void setBannerIDTest() {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00854796");
        Assert.assertTrue(user.getBannerId().equals("B00854796"));
    }

    @Test
    public void getBannerIDTest() {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00854796");
        Assert.assertTrue(user.getBannerId().equals("B00854796"));
    }

    @Test
    public void setFirstNameTest() {
        User user = modelAbstractFactory.createUser();
        user.setFirstName("Bob");
        Assert.assertTrue(user.getFirstName().equals("Bob"));
    }

    @Test
    public void getFirstNameTest() {
        User user = modelAbstractFactory.createUser();
        user.setFirstName("Bob");
        Assert.assertTrue(user.getFirstName().equals("Bob"));
    }

    @Test
    public void setLastNameTest() {
        User user = modelAbstractFactory.createUser();
        user.setLastName("Mcallister");
        Assert.assertTrue(user.getLastName().equals("Mcallister"));
    }

    @Test
    public void getLastNameTest() {
        User user = modelAbstractFactory.createUser();
        user.setLastName("Mcallister");
        Assert.assertTrue(user.getLastName().equals("Mcallister"));
    }

    @Test
    public void setEmailTest() {
        User user = modelAbstractFactory.createUser();
        user.setEmail("bob@gmail.com");
        Assert.assertTrue(user.getEmail().equals("bob@gmail.com"));
    }

    @Test
    public void getEmailTest() {
        User user = modelAbstractFactory.createUser();
        user.setEmail("rhawkey@dal.ca");
        Assert.assertTrue(user.getEmail().equals("rhawkey@dal.ca"));
    }


}
