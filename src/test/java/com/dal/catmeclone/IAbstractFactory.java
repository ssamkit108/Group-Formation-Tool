package com.dal.catmeclone;

import com.dal.catmeclone.UserProfileTest.IUserProfileAbstractFactory;
import com.dal.catmeclone.ValidationTest.IValidationAbstractFactory;
import com.dal.catmeclone.adminTest.IAdminAbstractFactory;
import com.dal.catmeclone.authenticationTest.IAuthenticationAbstractFactory;
import com.dal.catmeclone.coursesTest.ICourseAbstractFactory;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.notificationTest.INotificationAbstractFactory;
import com.dal.catmeclone.questionmanagementTest.IQuestionManagementAbstractFactory;
import com.dal.catmeclone.surveyresponseTest.ISurveyResponseAbstractFactory;

public interface IAbstractFactory {
    public IAdminAbstractFactory createAdminAbstractFactory();

    public ICourseAbstractFactory createCourseAbstractFactory();

    public IAuthenticationAbstractFactory createAuthenticationAbstractFactory();

    public INotificationAbstractFactory createNotificationAbstractFactory();

    public IQuestionManagementAbstractFactory createQuestionManagerAbstractFactory();

    public IUserProfileAbstractFactory createUserProfileAbstractFactory();

    public IValidationAbstractFactory createValidationAbstractFactory();

    public ISurveyResponseAbstractFactory createSurveyResponseAbstractFactory();

    public IModelAbstractFactory createModelAbstractFactory();


}
