package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.notificationTest.NotificationServiceMock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.UUID;

@SpringBootTest
@SuppressWarnings("deprecation")
class ForgotPasswordServiceTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    @Test
    void validatetoken() {
        User u = modelfact.createUser();
        u.setBannerId("B00852212");
        u.setToken("$10$P2FBvrlVJaS1/aEHzfKmtuojZollAx/F1g0DnvO7QvNv7/AUyihDu");

        Assert.isTrue(u.gettoken().equals("$10$P2FBvrlVJaS1/aEHzfKmtuojZollAx/F1g0DnvO7QvNv7/AUyihDu"));
    }

    @Test
    void ResetlinkTest() {
        String token = UUID.randomUUID().toString();
        NotificationServiceMock notificationservice = abstractFactoryTest.createNotificationAbstractFactory().createNotificationService();
        notificationservice.sendNotificationForPassword("B00852292", token, "ssamkit108@gmail.com");
        Assert.isTrue(notificationservice.success.equals("Sent"));
    }

    @Test
    void ValidateUserTest() {
        UserValidateMock check = abstractFactoryTest.createUserProfileAbstractFactory().createUserValidateMock();
        User u = modelfact.createUser();

        u.setBannerId("");
        Assert.isTrue(!check.validate(u));
        u = modelfact.createUser();
        u.setBannerId("B00825292");
        u.setEmail("bob123@gmail.com");
        u.setFirstName("Bob");
        u.setLastName("Shaw");
        u.setPassword("12345");
        Assert.isTrue(check.validate(u));
    }

    @Test
    void GeneratePasswordTest() {
        String token = UUID.randomUUID().toString();

        Assert.isTrue(!token.equals("password"));
    }
}
