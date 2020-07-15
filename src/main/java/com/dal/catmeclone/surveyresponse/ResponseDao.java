package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.Date;
import java.util.List;

public interface ResponseDao {
    List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception;

    void createResponseId(int surveyQuestionId, String bannerId, Date responseDate, boolean submitted, int courseid) throws Exception;

    void insertResponse(int surveyQuestionId, String bannerId, List<Object> response) throws Exception;

    Boolean checkPublished(int courseid) throws Exception;

    Boolean checkSubmitted(String bannerid, int courseid) throws Exception;
}
