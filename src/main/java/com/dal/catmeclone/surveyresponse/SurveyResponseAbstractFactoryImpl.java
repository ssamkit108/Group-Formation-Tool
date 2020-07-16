package com.dal.catmeclone.surveyresponse;

public class SurveyResponseAbstractFactoryImpl implements SurveyResponseAbstractFactory {
    public ResponseService createResponseService() {
        return new ResponseServiceImpl();
    }

    public ResponseDao createResponseDao() {
        return new ResponseDaoImpl();
    }

	@Override
	public ResponseService createResponseService(ResponseDao responseDao) {
		return new ResponseServiceImpl(responseDao);
	}
}
