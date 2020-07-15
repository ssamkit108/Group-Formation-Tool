package com.dal.catmeclone.surveyresponse;

public interface SurveyResponseAbstractFactory {
    ResponseService createResponseService();

    ResponseDao createResponseDao();
}
