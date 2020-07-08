package com.dal.catmeclone.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserSurveyResponse {

    private User user;
    private Survey survey;
    private Set<SurveyQuestionResponse> surveyResponse;
    private Date responseDate;
    private Boolean submitted;


    public UserSurveyResponse() {
        super();
    }

    public UserSurveyResponse(User user, Survey survey, Set<SurveyQuestionResponse> surveyResponse, Date responseDate,
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


    public Set<SurveyQuestionResponse> getSurveyResponse() {
        return surveyResponse;
    }


    public void setSurveyResponse(Set<SurveyQuestionResponse> surveyResponse) {
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
