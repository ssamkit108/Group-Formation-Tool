package com.dal.catmeclone.surveycreation;

public interface SurveyCreationAbstractFactory {

    CourseAdminSurveyService createCourseAdminSurveyService();

    CourseAdminSurveyService createCourseAdminSurveyService(CourseAdminSurveyDao courseAdminSurveyDao);

    CourseAdminSurveyDao createCourseAdminSurveyDao();
}
