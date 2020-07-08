package com.dal.catmeclone;

import com.dal.catmeclone.UserProfileTest.UserProfileAbstractFactoryImpl;
import com.dal.catmeclone.UserProfileTest.UserProfileAbstractFactoryTest;
import com.dal.catmeclone.ValidationTest.ValidationAbstractFactoryImpl;
import com.dal.catmeclone.ValidationTest.ValidationAbstractFactoryTest;
import com.dal.catmeclone.adminTest.AdminAbstractFactoryImpl;
import com.dal.catmeclone.adminTest.AdminAbstractFactoryTest;
import com.dal.catmeclone.authenticationTest.AuthenticationAbstractFactoryImpl;
import com.dal.catmeclone.authenticationTest.AuthenticationAbstractFactoryTest;
import com.dal.catmeclone.coursesTest.CourseAbstractFactoryImpl;
import com.dal.catmeclone.coursesTest.CourseAbstractFactoryTest;
import com.dal.catmeclone.notificationTest.NotificationAbstractFactoryImpl;
import com.dal.catmeclone.notificationTest.NotificationAbstractFactoryTest;
import com.dal.catmeclone.questionmanagementTest.QuestionManagementAbstractFactoryImpl;
import com.dal.catmeclone.questionmanagementTest.QuestionManagementAbstractFactoryTest;

public class AbstractFactoryTestImpl implements AbstractFactoryTest{

	@Override
	public AdminAbstractFactoryTest createAdminAbstractFactory() {
		// TODO Auto-generated method stub
		return new AdminAbstractFactoryImpl();
	}

	@Override
	public CourseAbstractFactoryTest createCourseAbstractFactory() {
		// TODO Auto-generated method stub
		return new CourseAbstractFactoryImpl();
	}

	@Override
	public AuthenticationAbstractFactoryTest createAuthenticationAbstractFactory() {
		// TODO Auto-generated method stub
		return new AuthenticationAbstractFactoryImpl();
	}


	@Override
	public NotificationAbstractFactoryTest createNotificationAbstractFactory() {
		// TODO Auto-generated method stub
		return new NotificationAbstractFactoryImpl();
	}

	@Override
	public QuestionManagementAbstractFactoryTest createQuestionManagerAbstractFactory() {
		// TODO Auto-generated method stub
		return new QuestionManagementAbstractFactoryImpl();
	}

	@Override
	public UserProfileAbstractFactoryTest createUserProfileAbstractFactory() {
		// TODO Auto-generated method stub
		return new UserProfileAbstractFactoryImpl();
	}

	@Override
	public ValidationAbstractFactoryTest createValidationAbstractFactory() {
		// TODO Auto-generated method stub
		return new ValidationAbstractFactoryImpl();
	}


}
