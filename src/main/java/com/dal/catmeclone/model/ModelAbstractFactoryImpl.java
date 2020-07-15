package com.dal.catmeclone.model;

public class ModelAbstractFactoryImpl implements ModelAbstractFactory {
    @Override
    public Course crateCourse() {
        return new Course();
    }

    @Override
    public BasicQuestion createBasicQuestion() {
        return new BasicQuestion();
    }

    @Override
    public MultipleChoiceQuestion createMultipleChoiceQuestion() {
        return new MultipleChoiceQuestion();
    }

    @Override
    public Option createOption() {
        return new Option();
    }

    @Override
    public Role createRole() {
        return new Role();
    }

    @Override
    public User createUser() {
        return new User();
    }

    @Override
    public Survey createSurvey() {
        return new Survey();
    }

    @Override
    public SurveyQuestion createSurveyQuestion() {
        return new SurveyQuestion();
    }

    @Override
    public SurveyQuestionResponse createSurveyQuestionResponse() {
        return new SurveyQuestionResponse();
    }

    @Override
    public UserSurveyResponse createUserSurveyResponse() {
        return new UserSurveyResponse();
    }
}
