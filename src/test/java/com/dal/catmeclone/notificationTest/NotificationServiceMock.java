package com.dal.catmeclone.notificationTest;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.notification.NotificationService;
import com.dal.catmeclone.notification.NotificationServiceImpl;

public class NotificationServiceMock implements NotificationService {

	final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Value("${from.email}")
	private String fromgmail;

	@Value("${from.password}")
	private String fromPassword;

	@Value("${account.subject}")
	private String subject;

	@Value("${login.url}")
	private String loginurl;

	String success = "";

	private void send(String from, String password, String to, String sub, String msg) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");

		// get Session
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromgmail, fromPassword);
			}
		});

		// compose message

		MimeMessage message = new MimeMessage(session);
		try {
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject(sub);
			message.setText(msg);
			// send message
			Transport.send(message);
			success = "Sent";
		} catch (MessagingException e) {
			logger.error("Error in sending email");
		}
	}

	@Override
	public void sendNotificationForPassword(String BannerId, String password, String sendto) {

		String body = "Hi" + "\n\n" + "Here is a new password for your account "
				+ "\nPlease find below your login credential: " + "\n\n\n" + "\nUsername: " + BannerId + "\nPassword: "
				+ password + "\n\nYou are most welcomed to be a part of this organisation."
				+ "\n\nBest Regards,\nCSCI5708-Grp12";
		body += "\nNote: for security reason, " + "you must change your password after logging in.";

		String subject = "Forgot password";
		send(fromgmail, fromPassword, sendto, subject, body);
	}

	@Override
	public void sendNotificationToNewuser(User user, String password, Course course) {

		String body = "<h2 >Hi " + user.getFirstName() + ",</h2>"
				+ "<p>Your Account has been created successfully and you have also been enrolled to subject: "
				+ course.getCourseID() + "</p>" + "<p>Please find below your login credential:&nbsp;</p>"
				+ "<p ><strong>Username: " + user.getBannerId() + "</strong></p>" + "<p ><strong>Password: "
				+ user.getPassword() + "</strong></p>"
				+ "<p style='text-align: center;'><strong> Please click on the button to log in</strong></p>"
				+ "<p style='text-align: center;'><a href=" + loginurl
				+ " target='_blank'><button style='background-color: #a0e9ed;'>Log In</button></a></p>"
				+ "<p style='text-align: left;'>&nbsp;</p>"
				+ "<p style='text-align: left;'>You are most welcomed to be a part of this organisation.</p>"
				+ "<p >Best Regards,</p>" + "<p>CSCI5708-Grp12</p>" + "<p>&nbsp;</p>";

		send(fromgmail, fromPassword, user.getEmail(), subject, body);

	}

}
