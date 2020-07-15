package com.dal.catmeclone.surveyresponseTest;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.surveyresponse.ResponseDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseServiceTest {

    IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
    IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
    ResponseDao ResponseDao = abstractFactoryTest.createSurveyResponseAbstractFactory().createResponseDao();

    int courseid;
    String bannerid;
    int surveyQuestionId;
    Date responseDate;
    boolean submitted;


    ResponseServiceTest() {
        this.bannerid = "B00832190";
        this.courseid = 300;
        this.surveyQuestionId = 12;
        this.responseDate = new Date();
        this.submitted = true;
    }

    @Test
    void getAllQuestion() throws Exception {
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
        ques.setSurveyQuestionId(13);
        ques.setQuestionDetail(details2);

        SurveyQuestionResponse resp2 = modelfact.createSurveyQuestionResponse();
        resp.setSurveyQuestion(ques2);
        surveyresp.add(resp2);

        assertEquals(ResponseDao.getAllQuestion(courseid).size(), surveyresp.size());
    }

    @Test
    void checkPublished() throws Exception {
        assertTrue(ResponseDao.checkPublished(courseid));
    }

    @Test
    void checkSubmitted() throws Exception {
        assertTrue(ResponseDao.checkSubmitted(bannerid, courseid));
    }

    @Test
    void setAllresponses() {
        User usr = modelfact.createUser();
        usr.setBannerId(bannerid);
        Course crc = modelfact.createCourse();
        crc.setCourseID(courseid);
        BasicQuestion details = modelfact.createBasicQuestion();
        details.setQuestionId(10);
        details.setQuestionText("rate yourself in java?");
        details.setQuestionTitle("java");
        details.setQuestionType(QuestionType.NUMERIC);


        SurveyQuestion ques = modelfact.createSurveyQuestion();
        ques.setSurveyQuestionId(surveyQuestionId);
        ques.setQuestionDetail(details);
        List<SurveyQuestion> lst = new ArrayList<>();
        lst.add(ques);
        Survey survey = modelfact.createSurvey();
        survey.setSurveyQuestions(lst);
        survey.setCourse(crc);
        SurveyQuestionResponse resp = modelfact.createSurveyQuestionResponse();
        resp.setSurveyQuestion(ques);
        ArrayList<Object> response = new ArrayList<>();
        response.add(5);
        resp.setResponse(response);
        List<SurveyQuestionResponse> surveyresp = new ArrayList<>();
        surveyresp.add(resp);
        List<UserSurveyResponse> usrlst = new ArrayList<>();
        UserSurveyResponse usrresp = modelfact.createUserSurveyResponse();
        usrresp.setResponseDate(responseDate);
        usrresp.setSubmitted(submitted);
        usrresp.setUser(usr);
        usrresp.setSurvey(survey);
        usrresp.setSurveyResponse(surveyresp);
        usrlst.add(usrresp);


    }
}