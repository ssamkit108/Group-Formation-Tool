package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.List;

public interface AlgorithmDao {
    public List<String> getAllStudents(int courseid) throws UserDefinedSQLException, Exception;

    public List<SurveyQuestionResponse> getAllResponsesOfAStudent(String bannerid, List<SurveyQuestion> listOfSurveyQuestions) throws UserDefinedSQLException, Exception;

    public List<SurveyQuestion> getSurveyQuestionsForCourse(int courseid) throws UserDefinedSQLException, Exception;

    public int getGroupSizeForCourse(int courseidd) throws UserDefinedSQLException;

    public Boolean updateGroupsFormed(List<List<String>> groups, int courseid) throws UserDefinedSQLException;

}
