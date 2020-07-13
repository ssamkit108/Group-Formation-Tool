package com.dal.catmeclone.surveycreation;

public interface SurveyCreationAbstractFactory {
    CourseAdminSurveyService createSurveyCreationService();
    CourseAdminSurveyDao createSurveyCreationDao();
}
