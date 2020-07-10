package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.Date;
import java.util.List;

public class ResponseDaoMock implements ResponseDao{

    @Override
    public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
        return null;
    }

    @Override
    public void createResponseId(int surveyQuestionId, String bannerId, Date responseDate, boolean submitted, int courseid) throws UserDefinedSQLException, Exception {

    }

    @Override
    public void insertResponse(int surveyQuestionId, String bannerId, List<Object> response) throws UserDefinedSQLException, Exception {

    }

    @Override
    public Boolean checkPublished(int courseid) throws Exception {
        return null;
    }

    @Override
    public Boolean checkSubmitted(String bannerid, int courseid) throws Exception {
        return null;
    }
}
