package com.dal.catmeclone.model;

import java.util.List;

/**
 * @author Mayank
 */
public class Survey {

    private int surveyId;
    private Course course;
    private List<SurveyQuestion> surveyQuestions;
    private boolean publishedStatus;
    private int groupSize;

    public Survey() {
        super();
    }

    /**
     * @param surveyId
     * @param course
     * @param surveyQuestions
     * @param publishedStatus
     * @param groupSize
     * @Constructor Parameterized Constructor to create Survey Object
     */
    public Survey(int surveyId, Course course, List<SurveyQuestion> surveyQuestions, boolean publishedStatus,
                  int groupSize) {
        super();
        this.surveyId = surveyId;
        this.course = course;
        this.surveyQuestions = surveyQuestions;
        this.publishedStatus = publishedStatus;
        this.groupSize = groupSize;
    }

    /**
     * @param course
     * @param surveyQuestions
     * @param publishedStatus
     * @param groupSize
     */
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

    @Override
    public String toString() {
        return "Survey [surveyId=" + surveyId + ", course=" + course + ", surveyQuestions=" + surveyQuestions
                + ", publishedStatus=" + publishedStatus + ", groupSize=" + groupSize + "]";
    }

}
