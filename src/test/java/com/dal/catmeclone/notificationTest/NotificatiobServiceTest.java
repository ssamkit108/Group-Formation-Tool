package com.dal.catmeclone.notificationTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class NotificatiobServiceTest {
	
    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();


	NotificationServiceMock notificationservice = new NotificationServiceMock();

	@Test
	public void sendNotificationToNewuserTest() {
		User u = abstractFactory.createModelAbstractFactory().createUser();		

		u.setBannerId("B00852232");
		u.setFirstName("Mayank");
		u.setLastName("Patel");

		u.setPassword("Password");
		u.setEmail("mayank@gmail.com");
		Course  c = abstractFactory.createModelAbstractFactory().crateCourse();		

		c.setCourseID(5409);
		c.setCourseName("Cloud Computing");

		notificationservice.sendNotificationToNewuser(u, "Password", c);

		Assert.assertTrue(notificationservice.success.equals("Sent"));
	}

	@Test
	public void sendNotificationForPasswordTest() {
		User u = abstractFactory.createModelAbstractFactory().createUser();		
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
