package com.dal.catmeclone.surveycreation;

public class SurveyCreationAbstractFactoryImpl implements SurveyCreationAbstractFactory {

	@Override
	public CourseAdminSurveyService createSurveyCreationService() {

		return new CourseAdminSurveyServiceImpl();
	}

	@Override
	public CourseAdminSurveyDao createSurveyCreationDao() {

		return new CourseAdminSurveyDaoImpl();
	}

}
