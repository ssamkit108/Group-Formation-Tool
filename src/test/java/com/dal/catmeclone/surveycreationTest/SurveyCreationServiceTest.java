package com.dal.catmeclone.surveycreationTest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyDao;
import com.dal.catmeclone.surveycreation.CourseAdminSurveyService;
import com.dal.catmeclone.surveycreation.SurveyCreationAbstractFactory;


public class SurveyCreationServiceTest {

	AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
    SurveyCreationAbstractFactory surveyCreationAbstractFactory = abstractFactoryTest.createSurveyCreationAbstractFactory();
    Survey survey;
    
    
   @BeforeEach
    public void set() {
    	survey = modelfact.createSurvey();
    	survey.setPublishedStatus(false);
    	survey.setGroupFormed(false);
    	survey.setGroupSize(3);
    	ArrayList<SurveyQuestion> surveyQuestionDetailsList = new ArrayList<SurveyQuestion>();
    	surveyQuestionDetailsList.add(modelfact.createSurveyQuestion(1, modelfact.createBasicQuestion(1, "Test Question", "Question 1", "NUMERIC"), "Group Similar", 2));
    	surveyQuestionDetailsList.add(modelfact.createSurveyQuestion(2, modelfact.createBasicQuestion(2, "Test Question", "Question 2", "FREETEXT"), "Group Disimilar", 0));
    	surveyQuestionDetailsList.add(modelfact.createSurveyQuestion(3, modelfact.createBasicQuestion(3, "Test Question", "Question 3", "MULTIPLECHOICEMANY"), "Group Disimilar", 0));
    	
    	survey.setSurveyQuestions(surveyQuestionDetailsList);
    }

    @Test
	public void getSurveyDetailsForCourseExistTest() throws UserDefinedException {
    	CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
		Course course = modelfact.createCourse(5709);
		
		Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course));
	}
    
    @Test
   	public void getSurveyDetailsForCourseNotExistTest() throws UserDefinedException {
       	CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
   		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
   		Course course = modelfact.createCourse(5708);
   		
   		Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course));
   	}

    @Test
	public void saveSurveyTestWhenSurveyNew() throws UserDefinedException {
		CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
   		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
   		survey.setSurveyId(0);
    	survey.setCourse(modelfact.createCourse(5708));
    	
   		Assert.assertTrue(surveyService.saveSurvey(survey));
	}
    
    @Test
   	public void saveSurveyTestWhenSurveyToBeUpdated() throws UserDefinedException {
   		CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
   		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
   		survey.setSurveyId(1);
    	survey.setCourse(modelfact.createCourse(5709));
    	survey.getSurveyQuestions().add(modelfact.createSurveyQuestion(4, modelfact.createBasicQuestion(4, "Test Question", "Question 4", "MULTIPLECHOICEONE"), "Group Similar", 0));
      	Assert.assertTrue(surveyService.saveSurvey(survey));
   	}

    @Test
	public void publishSurveyTest() throws UserDefinedException {
		CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
		
      	Assert.assertTrue(surveyService.publishSurvey(1));
   	}
    
    @Test
	public void getSurveyDetailsForCourseExistAndPublished() throws UserDefinedException {
    	CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
		CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
		Course course = modelfact.createCourse(5709);
		surveyService.publishSurvey(1);
		Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course).isPublishedStatus());
	}

}
