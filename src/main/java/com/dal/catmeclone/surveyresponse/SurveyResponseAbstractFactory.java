package com.dal.catmeclone.surveyresponse;

public interface SurveyResponseAbstractFactory {
    public ResponseService createResponseService();
    public ResponseDao createResponseDao();
}
