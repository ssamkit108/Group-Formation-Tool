package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.List;

public interface AlgorithmDao {
    public List<String> getAllStudents(int courseid) throws UserDefinedException, Exception;

    public List<SurveyQuestionResponse> getAllResponsesOfAStudent(String bannerid, List<SurveyQuestion> listOfSurveyQuestions) throws UserDefinedException, Exception;

    public List<SurveyQuestion> getSurveyQuestionsForCourse(int courseid) throws UserDefinedException, Exception;

    public int getGroupSizeForCourse(int courseidd) throws UserDefinedException;

    public Boolean updateGroupsFormed(List<List<String>> groups, int courseid) throws UserDefinedException;

}
