package com.dal.catmeclone.model;

import java.util.ArrayList;

public class SurveyQuestionResponse {

    private SurveyQuestion surveyQuestion;
    private ArrayList<Object> response;

    public SurveyQuestionResponse() {
        super();
    }

    /**
     * @param surveyQuestion
     * @param response
     */
    public SurveyQuestionResponse(SurveyQuestion surveyQuestion, ArrayList<Object> response) {
        super();
        this.surveyQuestion = surveyQuestion;
        this.response = response;
    }

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public ArrayList<Object> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Object> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SurveyQuestionResponse [surveyQuestion=" + surveyQuestion + ", response=" + response + "]";
    }

}
