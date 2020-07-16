package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.surveyresponse.ResponseDao;
import com.dal.catmeclone.surveyresponse.ResponseService;
import com.dal.catmeclone.surveyresponse.SurveyResponseAbstractFactory;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseServiceTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    SurveyResponseAbstractFactory surveyResponseFactory=abstractFactoryTest.createSurveyResponseAbstractFactory();
    ResponseDao responseDaoMock = surveyResponseFactory.createResponseDao();


    @Test
    void getAllSurveyQuestion() throws Exception {
    	ResponseService responseService = surveyResponseFactory.createResponseService(responseDaoMock);
       
        assertEquals(responseService.getAllQuestion(5709).size(),3);
    }

    @Test
    void setAllresponses() throws Exception {
    	Survey survey= modelFactory.createSurvey();
    	survey.setSurveyId(1);
        User user = modelFactory.createUser("B00839818");
        Course course = modelFactory.createCourse(5709);
        survey.setCourse(course);
        BasicQuestion details = modelFactory.createBasicQuestion();
        details.setQuestionId(10);
        details.setQuestionText("rate yourself in java?");
        details.setQuestionTitle("java");
        details.setQuestionType(QuestionType.NUMERIC);
        SurveyQuestion question = modelFactory.createSurveyQuestion(modelFactory.createBasicQuestion(1, "Test Question", "Question 1", "NUMERIC"));
        question.setSurveyQuestionId(1);
        ArrayList<Object> response = new ArrayList<>();
        response.add(5);
        SurveyQuestionResponse surveyQuestionResponse = modelFactory.createSurveyQuestionResponse(question, response);
        List<SurveyQuestionResponse> surveyResponse = new ArrayList<>();
        surveyResponse.add(surveyQuestionResponse);
        UserSurveyResponse userSurveyResponse= modelFactory.createUserSurveyResponse();
        userSurveyResponse.setResponseDate(new Date());
        userSurveyResponse.setSubmitted(true);
        userSurveyResponse.setUser(user);
        userSurveyResponse.setSurvey(survey);
        userSurveyResponse.setSurveyResponse(surveyResponse);
        
        ResponseService responseService = surveyResponseFactory.createResponseService(responseDaoMock);
        responseService.setAllresponses(userSurveyResponse);
        
        assertTrue(responseService.checkSubmitted(user.getBannerId(), course.getCourseID()));
        
    }
    
    @Test
    void checkSubmittedYes() throws Exception {
    	ResponseService responseService = surveyResponseFactory.createResponseService(responseDaoMock);
        assertTrue(responseService.checkSubmitted("B00839818", 5709));
    }
    
    @Test
    void checkSubmittedNo() throws Exception {
    	ResponseService responseService = surveyResponseFactory.createResponseService(responseDaoMock);
        Assert.assertFalse(responseService.checkSubmitted("B00000000", 5709));
    }
    
    @Test
    void checkPublished() throws Exception {
    	ResponseService responseService = surveyResponseFactory.createResponseService(responseDaoMock);
    	
        Assert.assertFalse(responseService.checkPublished(5709));
    }
}