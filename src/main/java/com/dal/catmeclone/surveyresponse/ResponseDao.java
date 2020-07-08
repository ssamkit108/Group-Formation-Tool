package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.List;

public interface ResponseDao {
    public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception;
}
