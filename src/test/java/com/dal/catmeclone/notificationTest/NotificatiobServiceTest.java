package com.dal.catmeclone.notificationTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;

@SpringBootTest
public class NotificatiobServiceTest {

	NotificationServiceMock notificationservice = new NotificationServiceMock();

	@Test
	void sendNotificationToNewuserTest() {
		User u = new User();
		u.setBannerId("B00852232");
		u.setFirstName("Mayank");
		u.setLastName("Patel");

		u.setPassword("Password");
		u.setEmail("mayank@gmail.com");

		Course c = new Course();
		c.setCourseID(5409);
		c.setCourseName("Cloud Computing");

		notificationservice.sendNotificationToNewuser(u, "Password", c);

		Assert.assertTrue(notificationservice.success.equals("sent"));
	}

	@Test
	void sendNotificationForPasswordTest() {
		User u = new User();
		u.setBannerId("B00852232");
		u.setFirstName("Mayank");
		u.setLastName("Patel");
		u.setPassword("Password");
		u.setEmail("mayank@gmail.com");
		String Bannerid = u.getBannerId();
		String password = u.getPassword();
		String sendto = u.getEmail();

		notificationservice.sendNotificationForPassword(Bannerid, password, sendto);

		Assert.assertTrue(notificationservice.success.equals("sent"));
	}
}
