package com.dal.catmeclone.notificationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.notification.NotificationService;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class NotificatiobServiceTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
    

    NotificationService notificationservice = abstractFactoryTest.createNotificationAbstractFactory().createNotificationService();

    @Test
    public void sendNotificationToNewuserTest() {
        User user = modelfact.createUser();

        user.setBannerId("B00852232");
        user.setFirstName("Mayank");
        user.setLastName("Patel");

        user.setPassword("Password");
        user.setEmail("mayank@gmail.com");
        Course c = modelfact.createCourse();

        c.setCourseID(5409);
        c.setCourseName("Cloud Computing");

        notificationservice.sendNotificationToNewuser(user, "Password", c);

        Assert.assertTrue(notificationservice.isStatus());
    }

    @Test
    public void sendNotificationForPasswordTest() {
        User u = modelfact.createUser();
        u.setBannerId("B00852232");
        u.setFirstName("Mayank");
        u.setLastName("Patel");
        u.setPassword("Password");
        u.setEmail("mayank@gmail.com");
        String Bannerid = u.getBannerId();
        String password = u.getPassword();
        String sendto = u.getEmail();

        notificationservice.sendNotificationForPassword(Bannerid, password, sendto);

        Assert.assertTrue(notificationservice.isStatus());
    }
}
