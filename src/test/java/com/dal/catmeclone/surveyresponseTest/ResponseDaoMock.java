package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.surveyresponse.ResponseDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseDaoMock implements ResponseDao {

	Survey survey;
	private List<SurveyQuestion> surveyQuestionDetailsList;
	private List<Survey> surveyList;
	static Map<Integer, String> responses = new HashMap<>();
	static int j, k = 0;

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
	ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();

	ResponseDaoMock() {
		super();
		this.survey = modelFactory.createSurvey();
		surveyQuestionDetailsList = new ArrayList<SurveyQuestion>();
		surveyList = new ArrayList<Survey>();
		this.survey.setSurveyId(1);
		this.survey.setCourse(modelFactory.createCourse(5709));
		this.survey.setPublishedStatus(false);
		this.survey.setGroupFormed(false);
		this.survey.setGroupSize(4);
		surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(1,
				modelFactory.createBasicQuestion(1, "Test Question", "Question 1", "NUMERIC"), "Group Similar", 3));
		surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(2,
				modelFactory.createBasicQuestion(2, "Test Question", "Question 2", "FREETEXT"), "Group Similar", 0));
		surveyQuestionDetailsList.add(modelFactory.createSurveyQuestion(3,
				modelFactory.createBasicQuestion(3, "Test Question", "Question 3", "MULTIPLECHOICEMANY"),
				"Group Disimilar", 0));
		this.survey.setSurveyQuestions(surveyQuestionDetailsList);
		surveyList.add(survey);
		Survey publishedSurvey= modelFactory.createSurvey(2, modelFactory.createCourse(6000), null, true, 4);
		surveyList.add(publishedSurvey);
		
		responses.put(100, "B00839818");

	}

	@Override
	public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
		List<SurveyQuestionResponse> surveyQuestionResponses = new ArrayList<SurveyQuestionResponse>();
		for (Survey survey : surveyList) {
			if (survey.getCourse().getCourseID() == courseid) {
				for (SurveyQuestion question : survey.getSurveyQuestions()) {
					SurveyQuestionResponse questionResponse = modelFactory.createSurveyQuestionResponse(question,
							new ArrayList<>());
					surveyQuestionResponses.add(questionResponse);
				}
			}
		}
		return surveyQuestionResponses;
	}

	@Override
	public void createResponseId(int surveyQuestionId, String bannerId, Date responseDate, boolean submitted,
			int courseid) throws UserDefinedException, Exception {
		responses.put(surveyQuestionId, bannerId);
		j++;
	}

	@Override
	public void insertResponse(int surveyQuestionId, String bannerId, List<Object> response)
			throws UserDefinedException, Exception {
		k = k + response.size();
	}

	@Override
	public Boolean checkPublished(int courseid) throws Exception {
		for (Survey survey : surveyList) {
			if (survey.getCourse().getCourseID() == courseid) {
				if (survey.isPublishedStatus()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Boolean checkSubmitted(String bannerid, int courseid) throws Exception {
		if (responses.containsValue(bannerid)) {
			return true;
		} else {
			return false;
		}
	}
}
