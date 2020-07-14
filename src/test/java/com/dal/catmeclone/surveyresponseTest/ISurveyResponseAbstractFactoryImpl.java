package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.surveyresponse.ResponseDao;
import com.dal.catmeclone.surveyresponse.ResponseService;
import com.dal.catmeclone.surveyresponse.ResponseServiceImpl;

public class ISurveyResponseAbstractFactoryImpl implements ISurveyResponseAbstractFactory{

	@Override
	public ResponseDao createResponseDao() {
		return new ResponseDaoMock();
	}

	@Override
	public ResponseService createResponseService() {
		// TODO Auto-generated method stub
		return new ResponseServiceImpl();
	}
	
}
