package com.dal.catmeclone.modelTest;

import com.dal.catmeclone.model.*;

public interface IModelAbstractFactory {
    public Course createCourse();

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
