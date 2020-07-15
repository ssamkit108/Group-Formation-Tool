package com.dal.catmeclone.model;

public interface ModelAbstractFactory {
    public Course crateCourse();

    public BasicQuestion createBasicQuestion();

    public MultipleChoiceQuestion createMultipleChoiceQuestion();

    public Option createOption();

    public Role createRole();

    public User createUser();

    public Survey createSurvey();

    public SurveyQuestion createSurveyQuestion();

    public SurveyQuestionResponse createSurveyQuestionResponse();

    public UserSurveyResponse createUserSurveyResponse();
}
