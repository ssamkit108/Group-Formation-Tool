package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.model.UserSurveyResponse;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.surveyresponse.ResponseDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResponseDaoMock implements ResponseDao{

	int courseid;
	String bannerId;
	String responseDate;
	boolean submitted;
	int surveyQuestionId;

	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();

	ResponseDaoMock(){
		super();
		this.courseid = 300;
		this.bannerId = "B00832190";
		this.responseDate = "2020-07-12";
		this.submitted = true;
		this.surveyQuestionId = 12;

	}
	@Override
	public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
		List<SurveyQuestionResponse> surveyresp = new ArrayList<>();

		BasicQuestion details = modelfact.createBasicQuestion();
		details.setQuestionId(12);
		details.setQuestionText("rate yourself in java?");
		details.setQuestionTitle("java");
		details.setQuestionType(QuestionType.NUMERIC);


		SurveyQuestion ques = modelfact.createSurveyQuestion();
		ques.setSurveyQuestionId(12);
		ques.setQuestionDetail(details);

		SurveyQuestionResponse resp = modelfact.createSurveyQuestionResponse();
		resp.setSurveyQuestion(ques);
		surveyresp.add(resp);

		BasicQuestion details2 = modelfact.createBasicQuestion();
		details.setQuestionId(13);
		details.setQuestionText("rate yourself in python?");
		details.setQuestionTitle("python");
		details.setQuestionType(QuestionType.NUMERIC);


		SurveyQuestion ques2 = modelfact.createSurveyQuestion();
		ques.setSurveyQuestionId(surveyQuestionId);
		ques.setQuestionDetail(details2);

		SurveyQuestionResponse resp2 = modelfact.createSurveyQuestionResponse();
		resp.setSurveyQuestion(ques2);
		surveyresp.add(resp2);

		return surveyresp;
	}

	@Override
	public void createResponseId(int surveyQuestionId, String bannerId, Date responseDate, boolean submitted, int courseid) throws UserDefinedSQLException, Exception {
		User usr = modelfact.createUser();
		usr.setBannerId(bannerId);
		Course crc = modelfact.createCourse();
		crc.setCourseID(courseid);
		SurveyQuestion surveyques = modelfact.createSurveyQuestion();
		surveyques.setSurveyQuestionId(surveyQuestionId);
		List<SurveyQuestion> lst = new ArrayList<>();
		lst.add(surveyques);
		Survey survey = modelfact.createSurvey();
		survey.setSurveyQuestions(lst);
		survey.setCourse(crc);
		SurveyQuestionResponse surresp = modelfact.createSurveyQuestionResponse();
		surresp.setSurveyQuestion(surveyques);
		List<SurveyQuestionResponse> resplst = new ArrayList<>();
		resplst.add(surresp);
		UserSurveyResponse usrresp = modelfact.createUserSurveyResponse();
		usrresp.setResponseDate(responseDate);
		usrresp.setSubmitted(submitted);
		usrresp.setUser(usr);
		usrresp.setSurvey(survey);
		usrresp.setSurveyResponse(resplst);

	}

	@Override
	public void insertResponse(int surveyQuestionId, String bannerId, List<Object> response) throws UserDefinedSQLException, Exception {
		
		
	}

	@Override
	public Boolean checkPublished(int courseid) throws Exception {
		if (courseid == this.courseid) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean checkSubmitted(String bannerid, int courseid) throws Exception {
		if ((courseid == this.courseid) && (bannerid == this.bannerId)) {
			return true;
		}
		else {
			return false;
		}	}
}
