package com.dal.catmeclone;

import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactoryImpl;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactoryImpl;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.Validation.ValidationAbstractFactoryImpl;
import com.dal.catmeclone.admin.AdminAbstractFacoryImpl;
import com.dal.catmeclone.admin.AdminAbstractFactory;
import com.dal.catmeclone.algorithm.AlgorithmAbstractFactory;
import com.dal.catmeclone.algorithm.AlgorithmAbstractFactoryImpl;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactory;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactoryImpl;
import com.dal.catmeclone.course.CourseAbstractFactory;
import com.dal.catmeclone.course.CourseAbstractFactoryImpl;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.encrypt.EncryptAbstractFactoryImpl;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.ModelAbstractFactoryImpl;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notification.NotificationAbstractFactoryImpl;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactoryImpl;
import com.dal.catmeclone.surveycreation.SurveyCreationAbstractFactory;
import com.dal.catmeclone.surveycreation.SurveyCreationAbstractFactoryImpl;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupAbstractFactory;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupAbstractFactoryImpl;
import com.dal.catmeclone.surveyresponse.SurveyResponseAbstractFactory;
import com.dal.catmeclone.surveyresponse.SurveyResponseAbstractFactoryImpl;

public class AbstractFactoryImpl implements AbstractFactory {
    @Override
    public AdminAbstractFactory createAdminAbstractFactory() {
        return new AdminAbstractFacoryImpl();
    }

    @Override
    public CourseAbstractFactory createCourseAbstractFactory() {
        return new CourseAbstractFactoryImpl();
    }

    @Override
    public AuthenticationAbstractFactory createAuthenticationAbstractFactory() {
        return new AuthenticationAbstractFactoryImpl();
    }

    @Override
    public DBUtilityAbstractFactory createDBUtilityAbstractFactory() {
        return new DBUtilityAbstractFactoryImpl();
    }

    @Override
    public EncryptAbstractFactory createEncryptAbstractFactory() {
        return new EncryptAbstractFactoryImpl();
    }

    @Override
    public ModelAbstractFactory createModelAbstractFactory() {
        return new ModelAbstractFactoryImpl();
    }

    @Override
    public NotificationAbstractFactory createNotificationAbstractFactory() {
        return new NotificationAbstractFactoryImpl();
    }

    @Override
    public QuestionManagementAbstractFactory createQuestionManagerAbstractFactory() {
        return new QuestionManagementAbstractFactoryImpl();
    }

    @Override
    public UserProfileAbstractFactory createUserProfileAbstractFactory() {
        return new UserProfileAbstractFactoryImpl();
    }

    @Override
    public ValidationAbstractFactory createValidationAbstractFactory() {
        return new ValidationAbstractFactoryImpl();
    }

    @Override
    public SurveyResponseAbstractFactory createSurveyResponseAbstractFactory() {
        return new SurveyResponseAbstractFactoryImpl();
    }

    @Override
    public SurveyCreationAbstractFactory createSurveyCreationAbstractFactory() {
        return new SurveyCreationAbstractFactoryImpl();
    }

    @Override
    public AlgorithmAbstractFactory createAlgorithmAbstractFactory() {
        return new AlgorithmAbstractFactoryImpl();
    }

    @Override
    public SurveyDisplayGroupAbstractFactory createSurveyDisplayGroupAbstractFactory() {
        return new SurveyDisplayGroupAbstractFactoryImpl();
    }

}
