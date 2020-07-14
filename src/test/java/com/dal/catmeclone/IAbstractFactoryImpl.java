package com.dal.catmeclone;

import com.dal.catmeclone.UserProfileTest.IUserProfileAbstractFactoryImpl;
import com.dal.catmeclone.UserProfileTest.IUserProfileAbstractFactory;
import com.dal.catmeclone.ValidationTest.IValidationAbstractFactoryImpl;
import com.dal.catmeclone.ValidationTest.IValidationAbstractFactory;
import com.dal.catmeclone.adminTest.IAdminAbstractFactoryImpl;
import com.dal.catmeclone.adminTest.IAdminAbstractFactory;
import com.dal.catmeclone.authenticationTest.IAuthenticationAbstractFactoryImpl;
import com.dal.catmeclone.authenticationTest.IAuthenticationAbstractFactory;
import com.dal.catmeclone.coursesTest.ICourseAbstractFactoryImpl;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.modelTest.IModelAbstractFactoryImpl;
import com.dal.catmeclone.coursesTest.ICourseAbstractFactory;
import com.dal.catmeclone.notificationTest.INotificationAbstractFactoryImpl;
import com.dal.catmeclone.notificationTest.INotificationAbstractFactory;
import com.dal.catmeclone.questionmanagementTest.IQuestionManagementAbstractFactoryImpl;
import com.dal.catmeclone.surveyresponseTest.ISurveyResponseAbstractFactory;
import com.dal.catmeclone.surveyresponseTest.ISurveyResponseAbstractFactoryImpl;
import com.dal.catmeclone.questionmanagementTest.IQuestionManagementAbstractFactory;

public class IAbstractFactoryImpl implements IAbstractFactory{

	@Override
	public IAdminAbstractFactory createAdminAbstractFactory() {
		// TODO Auto-generated method stub
		return new IAdminAbstractFactoryImpl();
	}

	@Override
	public ICourseAbstractFactory createCourseAbstractFactory() {
		// TODO Auto-generated method stub
		return new ICourseAbstractFactoryImpl();
	}

	@Override
	public IAuthenticationAbstractFactory createAuthenticationAbstractFactory() {
		// TODO Auto-generated method stub
		return new IAuthenticationAbstractFactoryImpl();
	}


	@Override
	public INotificationAbstractFactory createNotificationAbstractFactory() {
		// TODO Auto-generated method stub
		return new INotificationAbstractFactoryImpl();
	}

	@Override
	public IQuestionManagementAbstractFactory createQuestionManagerAbstractFactory() {
		// TODO Auto-generated method stub
		return new IQuestionManagementAbstractFactoryImpl();
	}

	@Override
	public IUserProfileAbstractFactory createUserProfileAbstractFactory() {
		// TODO Auto-generated method stub
		return new IUserProfileAbstractFactoryImpl();
	}

	@Override
	public IValidationAbstractFactory createValidationAbstractFactory() {
		// TODO Auto-generated method stub
		return new IValidationAbstractFactoryImpl();
	}

	@Override
	public ISurveyResponseAbstractFactory createSurveyResponseAbstractFactory() {
		return new ISurveyResponseAbstractFactoryImpl();
	}

	@Override
	public IModelAbstractFactory createModelAbstractFactory() {
		// TODO Auto-generated method stub
		return new IModelAbstractFactoryImpl();
	}


}
