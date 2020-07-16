package com.dal.catmeclone.surveycreation;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Survey;

public interface CourseAdminSurveyService {

	public Survey getSurveyDetailsForCourse(Course course)  throws UserDefinedException;
	public boolean saveSurvey(Survey survey)  throws UserDefinedException;
	public boolean publishSurvey(int surveyId)  throws UserDefinedException;
}
