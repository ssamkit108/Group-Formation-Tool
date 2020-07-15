package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest {
    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    @Test
    public void setBannerIDTest() {
        User u = modelfact.createUser();
        u.setBannerId("B00854796");
        Assert.isTrue(u.getBannerId().equals("B00854796"));
    }

    @Test
    public void getBannerIDTest() {
        User u = modelfact.createUser();
        u.setBannerId("B00854796");
        Assert.isTrue(u.getBannerId().equals("B00854796"));
    }

    @Test
    public void setFirstNameTest() {
        User u = modelfact.createUser();
        u.setFirstName("Bob");
        Assert.isTrue(u.getFirstName().equals("Bob"));
    }

    @Test
    public void getFirstNameTest() {
        User u = modelfact.createUser();
        u.setFirstName("Bob");
        Assert.isTrue(u.getFirstName().equals("Bob"));
    }

    @Test
    public void setLastNameTest() {
        User u = modelfact.createUser();
        u.setLastName("Mcallister");
        Assert.isTrue(u.getLastName().equals("Mcallister"));
    }

    @Test
    public void getLastNameTest() {
        User u = modelfact.createUser();
        u.setLastName("Mcallister");
        Assert.isTrue(u.getLastName().equals("Mcallister"));
    }

    @Test
    public void setEmailTest() {
        User u = modelfact.createUser();
        u.setEmail("bob@gmail.com");
        Assert.isTrue(u.getEmail().equals("bob@gmail.com"));
    }

    @Test
    public void getEmailTest() {
        User u = modelfact.createUser();
        u.setEmail("rhawkey@dal.ca");
        Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
    }


}
