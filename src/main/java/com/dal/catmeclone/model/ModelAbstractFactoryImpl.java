package com.dal.catmeclone.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelAbstractFactoryImpl implements ModelAbstractFactory {
    @Override
    public Course createCourse() {
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

	@Override
	public Survey createSurvey(int surveyId, Course course, List<SurveyQuestion> surveyQuestions,
			boolean publishedStatus, int groupSize) {
		return new Survey(surveyId, course, surveyQuestions, publishedStatus, groupSize);
	}

	@Override
	public Survey createSurvey(Course course, List<SurveyQuestion> surveyQuestions, boolean publishedStatus,
			int groupSize) {
		return new Survey(course, surveyQuestions, publishedStatus, groupSize);
	}

	@Override
	public SurveyQuestion createSurveyQuestion(BasicQuestion questionDetail) {
		return new SurveyQuestion(questionDetail);
	}

	@Override
	public SurveyQuestion createSurveyQuestion(int surveyQuestionId, BasicQuestion questionDetail,
			String algorithmLogicType, int logicConstraintValue) {
		return new SurveyQuestion(surveyQuestionId, questionDetail, algorithmLogicType, logicConstraintValue);
	}

	@Override
	public SurveyQuestion createSurveyQuestion(BasicQuestion questionDetail, String algorithmLogicType,
			int logicConstraintValue) {
		return new SurveyQuestion(questionDetail, algorithmLogicType, logicConstraintValue);
	}

	@Override
	public User createUser(String username) {
		return new User(username);
	}

	@Override
	public Course createCourse(int courseId) {
		return new Course(courseId);
	}

	@Override
	public BasicQuestion createBasicQuestion(int questionId, String questionTitle, String questionText,
			String questionType) {
		return new BasicQuestion(questionId, questionTitle, questionText, questionType);
	}

	@Override
	public BasicQuestion createBasicQuestion(String questionTitle, String questionText) {
		return new BasicQuestion(questionTitle, questionText);
	}

	@Override
	public BasicQuestion createBasicQuestion(String questionTitle, String questionText, QuestionType questionType,
			Date creationDate, User createdByInstructor) {
		return new BasicQuestion(questionTitle, questionText, questionType, creationDate, createdByInstructor);
	}

	@Override
	public Option createOption(String optionText, int optionValue) {
		return new Option(optionText, optionValue);
	}

	@Override
	public SurveyQuestionResponse createSurveyQuestionResponse(SurveyQuestion surveyQuestion,
			ArrayList<Object> response) {
		return new SurveyQuestionResponse(surveyQuestion, response);
	}
}
