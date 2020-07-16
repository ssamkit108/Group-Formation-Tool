package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.util.List;

public interface GroupFormationStrategy {

    public Boolean formatGroupForCourse(Course course) throws UserDefinedException, Exception;

    public List<List<String>> formGroup(List<List<Integer>> formattedResponses, List<String> criteria, List<String> studentsList, int groupSize);

    public void processMultipleChoiceAndFreeTextResponse(List<SurveyQuestionResponse> response);

    public void fetchAllDetailsRegardingSurvey(List<List<SurveyQuestionResponse>> userSurveyResponse, Course course) throws UserDefinedException, Exception;

    public List<String> getCriteriaList(List<SurveyQuestion> surveyQuestions);

    public List<List<Integer>> formatAllResponsesOfAllStudents(List<List<SurveyQuestionResponse>> userSurveyResponse, List<List<Integer>> formattedResponses);

}
