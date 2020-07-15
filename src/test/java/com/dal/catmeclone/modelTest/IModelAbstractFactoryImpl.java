package com.dal.catmeclone.modelTest;

import com.dal.catmeclone.model.*;

public class IModelAbstractFactoryImpl implements IModelAbstractFactory {

    public Course createCourse() {
        return new Course();
    }

    public BasicQuestion createBasicQuestion() {
        return new BasicQuestion();
    }

    public MultipleChoiceQuestion createMultipleChoiceQuestion() {
        return new MultipleChoiceQuestion();
    }

    public Option createOption() {
        return new Option();
    }

    public Role createRole() {
        return new Role();
    }

    public User createUser() {
        return new User();
    }

    public Survey createSurvey() {
        return new Survey();
    }

    public SurveyQuestion createSurveyQuestion() {
        return new SurveyQuestion();
    }

    public SurveyQuestionResponse createSurveyQuestionResponse() {
        return new SurveyQuestionResponse();
    }

    public UserSurveyResponse createUserSurveyResponse() {
        return new UserSurveyResponse();
    }

}
