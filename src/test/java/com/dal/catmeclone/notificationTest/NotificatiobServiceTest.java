package com.dal.catmeclone.notificationTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class NotificatiobServiceTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();


    NotificationServiceMock notificationservice = abstractFactoryTest.createNotificationAbstractFactory().createNotificationService();

    @Test
    public void sendNotificationToNewuserTest() {
        User u = modelfact.createUser();

        u.setBannerId("B00852232");
        u.setFirstName("Mayank");
        u.setLastName("Patel");

        u.setPassword("Password");
        u.setEmail("mayank@gmail.com");
        Course c = modelfact.createCourse();

        c.setCourseID(5409);
        c.setCourseName("Cloud Computing");

        notificationservice.sendNotificationToNewuser(u, "Password", c);

        Assert.assertTrue(notificationservice.success.equals("Sent"));
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

        Assert.assertTrue(notificationservice.success.equals("Sent"));
    }
}
