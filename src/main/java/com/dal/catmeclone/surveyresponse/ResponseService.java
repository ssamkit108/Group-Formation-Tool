package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.model.UserSurveyResponse;

import java.util.List;

public interface ResponseService {
    List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception;

    void setAllresponses(UserSurveyResponse userSurveyResponse) throws Exception;

    Boolean checkPublished(int courseid) throws Exception;

    Boolean checkSubmitted(String bannerid, int courseid) throws Exception;
}
