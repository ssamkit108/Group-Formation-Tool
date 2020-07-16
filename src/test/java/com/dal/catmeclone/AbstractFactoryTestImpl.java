package com.dal.catmeclone;

import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactoryImpl;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.UserProfileTest.UserProfileAbstractFactoryTestImpl;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.ValidationTest.ValidationAbstractFactoryTestImpl;
import com.dal.catmeclone.admin.AdminAbstractFactory;
import com.dal.catmeclone.algorithm.AlgorithmAbstractFactory;
import com.dal.catmeclone.adminTest.AdminAbstractFactoryTestImpl;
import com.dal.catmeclone.algorithm.AlgorithmAbstractFactoryImpl;
import com.dal.catmeclone.authenticationTest.AuthenticationAbstractFactoryTestImpl;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactory;
import com.dal.catmeclone.course.CourseAbstractFactory;
import com.dal.catmeclone.coursesTest.CourseAbstractFactoryTestImpl;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.encrypt.EncryptAbstractFactoryImpl;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.ModelAbstractFactoryImpl;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.notificationTest.NotificationAbstractFactoryTestImpl;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.questionmanagementTest.QuestionManagementAbstractFactoryTestImpl;
import com.dal.catmeclone.surveycreation.SurveyCreationAbstractFactory;
import com.dal.catmeclone.surveycreationTest.SurveyCreationAbstractFactoryTestImpl;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupAbstractFactory;
import com.dal.catmeclone.surveydisplaygroup.SurveyDisplayGroupAbstractFactoryImpl;
import com.dal.catmeclone.surveyresponse.SurveyResponseAbstractFactory;
import com.dal.catmeclone.surveyresponseTest.SurveyResponseAbstractFactoryTestImpl;

public class AbstractFactoryTestImpl implements AbstractFactory {

    @Override
    public AdminAbstractFactory createAdminAbstractFactory() {
        
        return new AdminAbstractFactoryTestImpl();
    }

    @Override
    public CourseAbstractFactory createCourseAbstractFactory() {
        
        return new CourseAbstractFactoryTestImpl();
    }

    @Override
    public AuthenticationAbstractFactory createAuthenticationAbstractFactory() {
        
        return new AuthenticationAbstractFactoryTestImpl();
    }


    @Override
    public NotificationAbstractFactory createNotificationAbstractFactory() {
        
        return new NotificationAbstractFactoryTestImpl();
    }

    @Override
    public QuestionManagementAbstractFactory createQuestionManagerAbstractFactory() {
        
        return new QuestionManagementAbstractFactoryTestImpl();
    }

    @Override
    public UserProfileAbstractFactory createUserProfileAbstractFactory() {
        
        return new UserProfileAbstractFactoryTestImpl();
    }

    @Override
    public ValidationAbstractFactory createValidationAbstractFactory() {
        
        return new ValidationAbstractFactoryTestImpl();
    }

    @Override
    public SurveyResponseAbstractFactory createSurveyResponseAbstractFactory() {
        return new SurveyResponseAbstractFactoryTestImpl();
    }

    @Override
    public ModelAbstractFactory createModelAbstractFactory() {
       
        return new ModelAbstractFactoryImpl();
    }

	@Override
	public SurveyCreationAbstractFactory createSurveyCreationAbstractFactory() {
		return new SurveyCreationAbstractFactoryTestImpl();
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
	public AlgorithmAbstractFactory createAlgorithmAbstractFactory() {
		return new AlgorithmAbstractFactoryImpl();
	}

	@Override
	public SurveyDisplayGroupAbstractFactory createSurveyDisplayGroupAbstractFactory() {
		return new SurveyDisplayGroupAbstractFactoryImpl();
	}

}
