package com.dal.catmeclone.model;
public class SurveyQuestion {

    private int surveyQuestionId;
    private MultipleChoiceQuestion questionDetail;
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
    public SurveyQuestion(int surveyQuestionId, MultipleChoiceQuestion questionDetail, String algorithmLogicType,
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
    public SurveyQuestion(MultipleChoiceQuestion questionDetail, String algorithmLogicType, int logicConstraintValue) {
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

    public MultipleChoiceQuestion getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(MultipleChoiceQuestion questionDetail) {
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
