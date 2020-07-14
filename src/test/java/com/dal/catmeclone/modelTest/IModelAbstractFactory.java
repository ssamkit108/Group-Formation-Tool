package com.dal.catmeclone.modelTest;

import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.model.Role;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.model.UserSurveyResponse;

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
