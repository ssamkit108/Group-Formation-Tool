package com.dal.catmeclone.surveycreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.model.User;

public interface CourseAdminSurveyService {

	public Survey getSurveyDetailsForCourse(Course course)  throws UserDefinedSQLException;
	public boolean saveSurvey(Survey survey)  throws UserDefinedSQLException;
	public boolean publishSurvey(int surveyId)  throws UserDefinedSQLException;
	public HashMap<String,List<User>> retrievegroupinfo(int courseid) throws UserDefinedSQLException;
	public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedSQLException;

}
