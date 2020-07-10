/**
 *
 */
package com.dal.catmeclone.model;

/**
 * @author Mayank
 *
 */
public class SurveyQuestion {

    private int surveyQuestionId;
    private BasicQuestion questionDetail;
    private String algorithmLogicType;
    private int logicConstraintValue;

    public SurveyQuestion() {
        super();
    }

    /**
     * @Constructor Parameterized Constructor to create SurveyQuestion Object
     *
     * @param surveyQuestionId
     * @param questionDetail
     * @param algorithmLogicType
     * @param logicConstraintValue
     */
    public SurveyQuestion(int surveyQuestionId, BasicQuestion questionDetail, String algorithmLogicType,
                          int logicConstraintValue) {
        super();
        this.surveyQuestionId = surveyQuestionId;
        this.questionDetail = questionDetail;
        this.algorithmLogicType = algorithmLogicType;
        this.logicConstraintValue = logicConstraintValue;
    }

    /**
     * @param questionDetail
     * @param algorithmLogicType
     * @param logicConstraintValue
     */
    public SurveyQuestion(BasicQuestion questionDetail, String algorithmLogicType, int logicConstraintValue) {
        super();
        this.questionDetail = questionDetail;
        this.algorithmLogicType = algorithmLogicType;
        this.logicConstraintValue = logicConstraintValue;
    }

    public int getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(int surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public BasicQuestion getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(BasicQuestion questionDetail) {
        this.questionDetail = questionDetail;
    }

    public String getAlgorithmLogicType() {
        return algorithmLogicType;
    }

    public void setAlgorithmLogicType(String algorithmLogicType) {
        this.algorithmLogicType = algorithmLogicType;
    }

    public int getLogicConstraintValue() {
        return logicConstraintValue;
    }

    public void setLogicConstraintValue(int logicConstraintValue) {
        this.logicConstraintValue = logicConstraintValue;
    }

    @Override
    public String toString() {
        return "SurveyQuestion [surveyQuestionId=" + surveyQuestionId + ", questionDetail=" + questionDetail
                + ", algorithmLogicType=" + algorithmLogicType + ", logicConstraintValue=" + logicConstraintValue + "]";
    }
}
