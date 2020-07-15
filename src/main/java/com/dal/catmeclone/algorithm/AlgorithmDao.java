package com.dal.catmeclone.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

public interface AlgorithmDao {
    public List<String> getAllStudents(int courseid) throws UserDefinedSQLException;
    
    public List<SurveyQuestionResponse> getAllResponsesOfAStudent(String bannerid, List<SurveyQuestion> listOfSurveyQuestions) throws UserDefinedSQLException;

    public List<SurveyQuestion> getSurveyQuestionsForCourse(int courseid) throws UserDefinedSQLException;

    public int getGroupSizeForCourse(int courseidd) throws UserDefinedSQLException;

}
