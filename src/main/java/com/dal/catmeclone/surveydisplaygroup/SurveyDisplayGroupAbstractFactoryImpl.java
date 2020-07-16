package com.dal.catmeclone.surveydisplaygroup;

public class SurveyDisplayGroupAbstractFactoryImpl implements SurveyDisplayGroupAbstractFactory {

	@Override
	public SurveyDisplayGroupService createSurveyDisplayGroupService() {
		return new SurveyDisplayGroupServiceImpl();
	}

	@Override
	public SurveyDisplayGroupService createSurveyDisplayGroupService(SurveyDisplayGroupDao surveyDisplayGroupDao) {
		return new SurveyDisplayGroupServiceImpl(surveyDisplayGroupDao);
	}

	@Override
	public SurveyDisplayGroupDao createSurveyDisplayGroupDao() {
		return new SurveyDisplayGroupDaoImpl();
	}

}
