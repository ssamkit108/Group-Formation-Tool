package com.dal.catmeclone.surveycreation;

public class SurveyCreationAbstractFactoryImpl implements SurveyCreationAbstractFactory {

    @Override
    public CourseAdminSurveyDao createCourseAdminSurveyDao() {
        return new CourseAdminSurveyDaoImpl();
    }

    @Override
    public CourseAdminSurveyService createCourseAdminSurveyService() {
        return new CourseAdminSurveyServiceImpl();
    }

    @Override
    public CourseAdminSurveyService createCourseAdminSurveyService(CourseAdminSurveyDao courseAdminSurveyDao) {
        return new CourseAdminSurveyServiceImpl(courseAdminSurveyDao);
    }

}
