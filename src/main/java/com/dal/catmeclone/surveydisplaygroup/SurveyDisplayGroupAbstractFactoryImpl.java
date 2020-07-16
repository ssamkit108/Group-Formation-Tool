package com.dal.catmeclone.surveydisplaygroup;

public class SurveyDisplayGroupAbstractFactoryImpl implements SurveyDisplayGroupAbstractFactory {

	@Override
	public SurveyDisplayGroupService createSurveyDisplayGroupService() {
		// TODO Auto-generated method stub
		return new SurveyDisplayGroupServiceImpl();
	}

	@Override
	public SurveyDisplayGroupService createSurveyDisplayGroupService(SurveyDisplayGroupDao surveyDisplayGroupDao) {
		// TODO Auto-generated method stub
		return new SurveyDisplayGroupServiceImpl();
	}

	@Override
	public SurveyDisplayGroupDao createSurveyDisplayGroupDao() {
		// TODO Auto-generated method stub
		return new SurveyDisplayGroupDaoImpl();
	}

}
