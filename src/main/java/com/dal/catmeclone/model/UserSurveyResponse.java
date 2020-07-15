package com.dal.catmeclone.model;

import java.util.Date;
import java.util.List;

public class UserSurveyResponse {

    public List<SurveyQuestionResponse> surveyResponse;
    private User user;
    private Survey survey;
    private Date responseDate;
    private Boolean submitted;


    public UserSurveyResponse() {
        super();
    }

    public UserSurveyResponse(User user, Survey survey, List<SurveyQuestionResponse> surveyResponse, Date responseDate,
                              Boolean submitted) {
        super();
        this.user = user;
        this.survey = survey;
        this.surveyResponse = surveyResponse;
        this.responseDate = responseDate;
        this.submitted = submitted;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Survey getSurvey() {
        return survey;
    }


    public void setSurvey(Survey survey) {
        this.survey = survey;
    }


    public List<SurveyQuestionResponse> getSurveyResponse() {
        return surveyResponse;
    }


    public void setSurveyResponse(List<SurveyQuestionResponse> surveyResponse) {
        this.surveyResponse = surveyResponse;
    }


    public Date getResponseDate() {
        return responseDate;
    }


    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }


    public Boolean getSubmitted() {
        return submitted;
    }


    public void setSubmitted(Boolean submitted) {
        this.submitted = submitted;
    }


    @Override
    public String toString() {
        return "UserSurveyResponse [user=" + user + ", survey=" + survey + ", surveyResponse=" + surveyResponse
                + ", responseDate=" + responseDate + ", submitted=" + submitted + "]";
    }
}
