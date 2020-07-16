package com.dal.catmeclone.surveyresponse;

public interface SurveyResponseAbstractFactory {
    ResponseService createResponseService();

    ResponseService createResponseService(ResponseDao responseDao);

    ResponseDao createResponseDao();
}
