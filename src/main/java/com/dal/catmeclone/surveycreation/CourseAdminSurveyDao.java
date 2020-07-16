package com.dal.catmeclone.surveycreation;

import java.util.List;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;

public interface CourseAdminSurveyDao {

    public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedException;
    public boolean createSurveyDetails(Survey survey) throws UserDefinedException;
    public boolean updateSurveyDetails(Survey survey, List<SurveyQuestion> surveyQuestionsToBeRemoved) throws UserDefinedException;
    public boolean publishSurvey(int surveyId) throws UserDefinedException;

}
