package com.dal.catmeclone.model;

import java.util.List;

public class Survey {

    private int surveyId;
    private Course course;
    private List<SurveyQuestion> surveyQuestions;
    private boolean publishedStatus;
    private int groupSize;
    private boolean groupFormed;

    public Survey() {
        super();
    }

    public Survey(int surveyId, Course course, List<SurveyQuestion> surveyQuestions, boolean publishedStatus,
                  int groupSize) {
        super();
        this.surveyId = surveyId;
        this.course = course;
        this.surveyQuestions = surveyQuestions;
        this.publishedStatus = publishedStatus;
        this.groupSize = groupSize;
    }

    public Survey(Course course, List<SurveyQuestion> surveyQuestions, boolean publishedStatus, int groupSize) {
        super();
        this.course = course;
        this.surveyQuestions = surveyQuestions;
        this.publishedStatus = publishedStatus;
        this.groupSize = groupSize;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<SurveyQuestion> getSurveyQuestions() {
        return surveyQuestions;
    }

    public void setSurveyQuestions(List<SurveyQuestion> surveyQuestions) {
        this.surveyQuestions = surveyQuestions;
    }

    public boolean isPublishedStatus() {
        return publishedStatus;
    }

    public void setPublishedStatus(boolean publishedStatus) {
        this.publishedStatus = publishedStatus;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public boolean isGroupFormed() {
        return groupFormed;
    }

    public void setGroupFormed(boolean groupFormed) {
        this.groupFormed = groupFormed;
    }

    @Override
    public String toString() {
        return "Survey [surveyId=" + surveyId + ", course=" + course + ", surveyQuestions=" + surveyQuestions
                + ", publishedStatus=" + publishedStatus + ", groupSize=" + groupSize + "]";
    }

}
