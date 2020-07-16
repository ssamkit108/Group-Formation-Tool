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
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();


    NotificationService notificationservice = abstractFactoryTest.createNotificationAbstractFactory().createNotificationService();

    @Test
    public void sendNotificationToNewuserTest() {
        User user = modelAbstractFactory.createUser();

        user.setBannerId("B00852232");
        user.setFirstName("Mayank");
        user.setLastName("Patel");

        user.setPassword("Password");
        user.setEmail("mayank@gmail.com");
        Course course = modelAbstractFactory.createCourse();

        course.setCourseID(5409);
        course.setCourseName("Cloud Computing");

        notificationservice.sendNotificationToNewuser(user, "Password", course);

        Assert.assertTrue(notificationservice.isStatus());
    }

    @Test
    public void sendNotificationForPasswordTest() {
        User user = modelAbstractFactory.createUser();
        user.setBannerId("B00852232");
        user.setFirstName("Mayank");
        user.setLastName("Patel");
        user.setPassword("Password");
        user.setEmail("mayank@gmail.com");
        String Bannerid = user.getBannerId();
        String password = user.getPassword();
        String sendto = user.getEmail();

        notificationservice.sendNotificationForPassword(Bannerid, password, sendto);

        Assert.assertTrue(notificationservice.isStatus());
    }
}
