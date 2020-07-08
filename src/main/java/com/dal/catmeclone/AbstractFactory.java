package com.dal.catmeclone;

import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.UserProfile.UserProfileAbstractFactory;
import com.dal.catmeclone.Validation.ValidationAbstractFactory;
import com.dal.catmeclone.admin.AdminAbstractFactory;
import com.dal.catmeclone.authenticationandauthorization.AuthenticationAbstractFactory;
import com.dal.catmeclone.course.CourseAbstractFactory;
import com.dal.catmeclone.encrypt.EncryptAbstractFactory;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.notification.NotificationAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.surveyresponse.SurveyResponseAbstractFactory;

public interface AbstractFactory {
    public AdminAbstractFactory createAdminAbstractFactory();
    public CourseAbstractFactory createCourseAbstractFactory();
    public AuthenticationAbstractFactory createAuthenticationAbstractFactory();
    public DBUtilityAbstractFactory createDBUtilityAbstractFactory();
    public EncryptAbstractFactory createEncryptAbstractFactory();
    public ModelAbstractFactory createModelAbstractFactory();
    public NotificationAbstractFactory createNotificationAbstractFactory();
    public QuestionManagementAbstractFactory createQuestionManagerAbstractFactory();
    public UserProfileAbstractFactory createUserProfileAbstractFactory();
    public ValidationAbstractFactory createValidationAbstractFactory();
    public SurveyResponseAbstractFactory createSurveyResponseAbstractFactory();
}
