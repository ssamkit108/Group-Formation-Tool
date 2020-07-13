package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.surveyresponse.ResponseDao;
import com.dal.catmeclone.surveyresponse.ResponseService;

public interface ISurveyResponseAbstractFactory {
	public ResponseDao createResponseDao();
	public ResponseService createResponseService();
}
