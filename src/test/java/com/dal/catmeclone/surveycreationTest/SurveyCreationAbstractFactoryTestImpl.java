package com.dal.catmeclone.surveycreationTest;

import com.dal.catmeclone.surveycreation.CourseAdminSurveyDao;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyService;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyServiceImpl;
import com.dal.catmeclone.surveycreation.SurveyCreationAbstractFactory;

public class SurveyCreationAbstractFactoryTestImpl implements SurveyCreationAbstractFactory {

    @Override
    public CourseAdminSurveyDao createCourseAdminSurveyDao() {
        return new SurveyCreationDaoMock();
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
