package com.dal.catmeclone.surveycreationTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyDao;
import java.util.ArrayList;
import java.util.List;

public class SurveyCreationDaoMock implements CourseAdminSurveyDao {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    
    private ArrayList<SurveyQuestion> surveyQuestionDetailsList;
    private ArrayList<Survey> surveyList;
    private Survey survey= null;

    public SurveyCreationDaoMock() {
    	this.survey = modelFactory.createSurvey();
    	surveyQuestionDetailsList= new ArrayList<SurveyQuestion>();
    	surveyList = new ArrayList<Survey>();
    	this.survey.setSurveyId(1);
    	this.survey.setCourse(modelFactory.createCourse(5709));
    	this.survey.setPublishedStatus(false);
    	this.survey.setGroupFormed(false);
    	this.survey.setGroupSize(4);
    	surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(1, modelFactory.createBasicQuestion(1, "Test Question", "Question 1", "NUMERIC"), "Group Similar", 3));
    	surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(2, modelFactory.createBasicQuestion(2, "Test Question", "Question 2", "FREETEXT"), "Group Similar", 0));
    	surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(3, modelFactory.createBasicQuestion(3, "Test Question", "Question 3", "MULTIPLECHOICEMANY"), "Group Disimilar", 0));
    	this.survey.setSurveyQuestions(surveyQuestionDetailsList);
    	surveyList.add(survey);
    }

	@Override
	public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedException {
		Survey surveyFetched=null;
		for(Survey survey: this.surveyList)
		{
			if(survey.getCourse().getCourseID()==course.getCourseID())
			{
				surveyFetched = survey;
			}
		}
		return surveyFetched;
	}

	@Override
	public boolean createSurveyDetails(Survey survey) throws UserDefinedException {
		surveyList.add(survey);
		return true;
	}

	@Override
	public boolean updateSurveyDetails(Survey survey, List<SurveyQuestion> surveyQuestionsToBeRemoved)
			throws UserDefinedException {
		int j=0;
		for(Survey surveyfetched:surveyList)
		{
			if(surveyfetched.getSurveyId()==survey.getSurveyId())
			{
				surveyList.set(j, survey);
			}
			j++;
		}
		return true;
	}

	@Override
	public boolean publishSurvey(int surveyId) throws UserDefinedException {
		for(Survey survey: this.surveyList)
		{
			if(survey.getSurveyId()==surveyId)
			{
				survey.setPublishedStatus(true);
			}
		}
		return true;
	}


}
