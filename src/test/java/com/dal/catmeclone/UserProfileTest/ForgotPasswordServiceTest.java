package com.dal.catmeclone.UserProfileTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.UserProfile.ForgotPasswordDao;
import com.dal.catmeclone.UserProfile.ForgotPasswordService;
import com.dal.catmeclone.UserProfile.ForgotPasswordServiceImpl;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.notification.NotificationService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

@SpringBootTest
class ForgotPasswordServiceTest {

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();

	ForgotPasswordDao forgotPasswordDaoMock;
	ForgotPasswordService forgotPasswordServiceImpl;

	public ForgotPasswordServiceTest() {
		forgotPasswordDaoMock = mock(ForgotPasswordDao.class);
		forgotPasswordServiceImpl = mock(ForgotPasswordServiceImpl.class);
	}

	@BeforeEach
	public void setup() {
		forgotPasswordServiceImpl = abstractFactoryTest.createUserProfileAbstractFactory()
				.createForgotPasswordService(forgotPasswordDaoMock);
	}

	@Test
	public void validatetoken() throws Exception {
		String token = "test";
		when(forgotPasswordDaoMock.checkTokenExist(token)).thenReturn("test");
		assertEquals(forgotPasswordServiceImpl.validateToken(token), "test");
	}

	@Test
	void ResetlinkTest() {
		String token = UUID.randomUUID().toString();
		NotificationService notificationservice = abstractFactoryTest.createNotificationAbstractFactory().createNotificationService();
		notificationservice.sendNotificationForPassword("B00852292", token, "ssamkit108@gmail.com");
		Assert.assertTrue(notificationservice.isStatus());
	}

	@Test
	void GeneratePasswordTest() {
		String token = UUID.randomUUID().toString();
		Assert.assertTrue(!token.equals("password"));
	}
}
