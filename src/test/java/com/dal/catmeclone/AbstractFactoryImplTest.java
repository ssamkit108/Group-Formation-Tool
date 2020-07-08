package com.dal.catmeclone;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

class AbstractFactoryImplTest {

    AbstractFactory abstractFactory=SystemConfig.instance().getAbstractFactory();

    @Test
    void createAdminAbstractFactory() {
        assertTrue(abstractFactory.createAdminAbstractFactory() instanceof  AdminAbstractFactory);
    }

    @Test
    void createCourseAbstractFactory() {
        assertTrue(abstractFactory.createCourseAbstractFactory() instanceof CourseAbstractFactory);
    }

    @Test
    void createAuthenticationAbstractFactory() {
        assertTrue(abstractFactory.createAuthenticationAbstractFactory() instanceof  AuthenticationAbstractFactory);
    }

    @Test
    void createDBUtilityAbstractFactory() {
        assertTrue(abstractFactory.createDBUtilityAbstractFactory() instanceof  DBUtilityAbstractFactory);
    }

    @Test
    void createEncryptAbstractFactory() {
        assertTrue(abstractFactory.createEncryptAbstractFactory() instanceof EncryptAbstractFactory);
    }

    @Test
    void createModelAbstractFactory() {
        assertTrue(abstractFactory.createModelAbstractFactory() instanceof  ModelAbstractFactory);
    }

    @Test
    void createNotificationAbstractFactory() {
        assertTrue(abstractFactory.createNotificationAbstractFactory() instanceof NotificationAbstractFactory);
    }

    @Test
    void createQuestionManagerAbstractFactory() {
        assertTrue(abstractFactory.createQuestionManagerAbstractFactory() instanceof QuestionManagementAbstractFactory);
    }

    @Test
    void createUserProfileAbstractFactory() {
        assertTrue(abstractFactory.createUserProfileAbstractFactory() instanceof  UserProfileAbstractFactory);
    }

    @Test
    void createValidationAbstractFactory() {
        assertTrue(abstractFactory.createValidationAbstractFactory() instanceof ValidationAbstractFactory);
    }
}