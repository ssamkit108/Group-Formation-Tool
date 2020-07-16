package com.dal.catmeclone.surveycreationTest;

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
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class SurveyCreationServiceTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelAbstractFactory = abstractFactoryTest.createModelAbstractFactory();
    SurveyCreationAbstractFactory surveyCreationAbstractFactory = abstractFactoryTest.createSurveyCreationAbstractFactory();
    Survey survey;


    @BeforeEach
    public void set() {
        survey = modelAbstractFactory.createSurvey();
        survey.setPublishedStatus(false);
        survey.setGroupFormed(false);
        survey.setGroupSize(3);
        ArrayList<SurveyQuestion> surveyQuestionDetailsList = new ArrayList<SurveyQuestion>();
        surveyQuestionDetailsList.add(modelAbstractFactory.createSurveyQuestion(1, modelAbstractFactory.createBasicQuestion(1, "Test Question", "Question 1", "NUMERIC"), "Group Similar", 2));
        surveyQuestionDetailsList.add(modelAbstractFactory.createSurveyQuestion(2, modelAbstractFactory.createBasicQuestion(2, "Test Question", "Question 2", "FREETEXT"), "Group Disimilar", 0));
        surveyQuestionDetailsList.add(modelAbstractFactory.createSurveyQuestion(3, modelAbstractFactory.createBasicQuestion(3, "Test Question", "Question 3", "MULTIPLECHOICEMANY"), "Group Disimilar", 0));

        survey.setSurveyQuestions(surveyQuestionDetailsList);
    }

    @Test
    public void getSurveyDetailsForCourseExistTest() throws UserDefinedException {
        CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
        CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
        Course course = modelAbstractFactory.createCourse(5709);

        Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course));
    }

    @Test
    public void getSurveyDetailsForCourseNotExistTest() throws UserDefinedException {
        CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
        CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
        Course course = modelAbstractFactory.createCourse(5708);

        Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course));
    }

    @Test
    public void saveSurveyTestWhenSurveyNew() throws UserDefinedException {
        CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
        CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
        survey.setSurveyId(0);
        survey.setCourse(modelAbstractFactory.createCourse(5708));

        Assert.assertTrue(surveyService.saveSurvey(survey));
    }

    @Test
    public void saveSurveyTestWhenSurveyToBeUpdated() throws UserDefinedException {
        CourseAdminSurveyDao surveyDaoMock = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
        CourseAdminSurveyService surveyService = surveyCreationAbstractFactory.createCourseAdminSurveyService(surveyDaoMock);
        survey.setSurveyId(1);
        survey.setCourse(modelAbstractFactory.createCourse(5709));
        survey.getSurveyQuestions().add(modelAbstractFactory.createSurveyQuestion(4, modelAbstractFactory.createBasicQuestion(4, "Test Question", "Question 4", "MULTIPLECHOICEONE"), "Group Similar", 0));
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
        Course course = modelAbstractFactory.createCourse(5709);
        surveyService.publishSurvey(1);
        Assert.assertNotNull(surveyService.getSurveyDetailsForCourse(course).isPublishedStatus());
    }

}
