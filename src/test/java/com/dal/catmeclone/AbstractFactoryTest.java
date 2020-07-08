package com.dal.catmeclone;

import com.dal.catmeclone.UserProfileTest.UserProfileAbstractFactoryTest;
import com.dal.catmeclone.ValidationTest.ValidationAbstractFactoryTest;
import com.dal.catmeclone.adminTest.AdminAbstractFactoryTest;
import com.dal.catmeclone.authenticationTest.AuthenticationAbstractFactoryTest;
import com.dal.catmeclone.coursesTest.CourseAbstractFactoryTest;
import com.dal.catmeclone.notificationTest.NotificationAbstractFactoryTest;
import com.dal.catmeclone.questionmanagementTest.QuestionManagementAbstractFactoryTest;

public interface AbstractFactoryTest {
    public AdminAbstractFactoryTest createAdminAbstractFactory();
    public CourseAbstractFactoryTest createCourseAbstractFactory();
    public AuthenticationAbstractFactoryTest createAuthenticationAbstractFactory();
    public NotificationAbstractFactoryTest createNotificationAbstractFactory();
    public QuestionManagementAbstractFactoryTest createQuestionManagerAbstractFactory();
    public UserProfileAbstractFactoryTest createUserProfileAbstractFactory();
    public ValidationAbstractFactoryTest createValidationAbstractFactory();

}
