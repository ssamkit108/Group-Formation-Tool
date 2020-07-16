package com.dal.catmeclone.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ModelAbstractFactory {
	public Course createCourse();
	
	public Course createCourse(int courseId);

	public BasicQuestion createBasicQuestion();

	public MultipleChoiceQuestion createMultipleChoiceQuestion();

	public Option createOption();
	
	public Option createOption(String optionText, int optionValue);

	public Role createRole();

	public User createUser();
	
	public User createUser(String username);

	public Survey createSurvey();

	public SurveyQuestion createSurveyQuestion();

	public SurveyQuestionResponse createSurveyQuestionResponse();

	public UserSurveyResponse createUserSurveyResponse();

	public Survey createSurvey(int surveyId, Course course, List<SurveyQuestion> surveyQuestions,
			boolean publishedStatus, int groupSize);

	public Survey createSurvey(Course course, List<SurveyQuestion> surveyQuestions, boolean publishedStatus,
			int groupSize);

	public SurveyQuestion createSurveyQuestion(BasicQuestion questionDetail);

	public SurveyQuestion createSurveyQuestion(int surveyQuestionId, BasicQuestion questionDetail,
			String algorithmLogicType, int logicConstraintValue);

	public SurveyQuestion createSurveyQuestion(BasicQuestion questionDetail, String algorithmLogicType,
			int logicConstraintValue);
	
	public BasicQuestion createBasicQuestion(int questionId, String questionTitle, String questionText, String questionType);

	public BasicQuestion createBasicQuestion(String questionTitle, String questionText);

	public BasicQuestion createBasicQuestion(String questionTitle, String questionText, QuestionType questionType, Date creationDate,
	                         User createdByInstructor);
	
	public SurveyQuestionResponse createSurveyQuestionResponse(SurveyQuestion surveyQuestion, ArrayList<Object> response);
}
