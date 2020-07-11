package com.dal.catmeclone.surveycreation;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;

public interface CourseAdminSurveyDao {

	public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedSQLException;
	public boolean createSurveyDetails(Survey survey) throws UserDefinedSQLException;
	public boolean updateSurveyDetails(Survey survey, List<SurveyQuestion> surveyQuestionsToBeRemoved) throws UserDefinedSQLException;
	public boolean publishSurvey(int surveyId) throws UserDefinedSQLException;
}
