package com.dal.catmeclone.surveycreation;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CourseAdminSurveyServiceImpl implements CourseAdminSurveyService {

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    SurveyCreationAbstractFactory surveyCreationAbstractFactory = abstractFactory.createSurveyCreationAbstractFactory();
    ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
    CourseAdminSurveyDao courseAdminSurveyDao = surveyCreationAbstractFactory.createCourseAdminSurveyDao();
    private Logger LOGGER = Logger.getLogger(CourseAdminSurveyController.class.getName());

    public CourseAdminSurveyServiceImpl() {
        super();
    }

    public CourseAdminSurveyServiceImpl(CourseAdminSurveyDao courseAdminSurveyDao) {
        super();
        this.courseAdminSurveyDao = courseAdminSurveyDao;
    }

    @Override
    public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedException {

        LOGGER.info("Calling Dao Methods to retrieve the details form database");
        Survey survey = courseAdminSurveyDao.getSurveyDetailsForCourse(course);
        if (survey == null) {
            LOGGER.info("No Survey Exists in database for the course");
            survey = modelAbstractFactory.createSurvey(0, course, new ArrayList<SurveyQuestion>(), false, 2);
        }
        return survey;
    }

    @Override
    public boolean saveSurvey(Survey survey) throws UserDefinedException {

        List<SurveyQuestion> listOfQuestionsToBeRemoved = new ArrayList<SurveyQuestion>();
        if (survey.getSurveyId() == 0) {
            // Calling DAO to insert new survey into DB
            LOGGER.info("Surevy identifies as a new survey. Calling Dao to insert survey into database");
            courseAdminSurveyDao.createSurveyDetails(survey);
        } else {

            LOGGER.info("Survey identifies as a existing survey. Identifying the updates");
            // getting existing Survey Question for the survey
            Survey existingsSurvey = courseAdminSurveyDao.getSurveyDetailsForCourse(survey.getCourse());
            List<SurveyQuestion> listOfExistingSurveyQuestion = existingsSurvey.getSurveyQuestions();
            listOfQuestionsToBeRemoved = listOfExistingSurveyQuestion;

            LOGGER.info("Segregating the survey questions which has to be update and which needs to be removed");
            // Identifying and segregating the survey questions which has to be update and which needs to be removed.
            listOfExistingSurveyQuestion.removeAll(survey.getSurveyQuestions());

            LOGGER.info("Calling DAO to update survey details into database");
            courseAdminSurveyDao.updateSurveyDetails(survey, listOfQuestionsToBeRemoved);
        }
        return true;
    }

    @Override
    public boolean publishSurvey(int surveyId) throws UserDefinedException {
        return courseAdminSurveyDao.publishSurvey(surveyId);
    }

}
