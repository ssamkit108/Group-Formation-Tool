package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Logger;

public class ResponseDaoImpl implements ResponseDao {

    Logger LOGGER = Logger.getLogger(ResponseDaoImpl.class.getName());

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
    Properties properties = SystemConfig.instance().getProperties();
    ResultSet resultSet;
    ResultSet resultSet1;
    private DataBaseConnection DBUtil;
    private Connection connection;

    public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        List<SurveyQuestionResponse> listOfQuestions = new ArrayList<SurveyQuestionResponse>();
        CallableStatement statement = null;
        CallableStatement statement_options = null;
        Map<Integer, Integer> survey_question = new TreeMap<Integer, Integer>();
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_surveryquestions") + "}");
            statement.setInt(1, courseid);
            LOGGER.info("Querying Database to fetch list of question for the survey");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                survey_question.put(resultSet.getInt(1), resultSet.getInt(2));
            }

            for (Map.Entry<Integer, Integer> entry : survey_question.entrySet()) {
                SurveyQuestionResponse surveyQuestionResponse = modelAbstractFactory.createSurveyQuestionResponse();
                SurveyQuestion surveyQuestion = modelAbstractFactory.createSurveyQuestion();
                surveyQuestion.setSurveyQuestionId(entry.getKey());
                statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_basicquestion") + "}");
                statement.setInt(1, entry.getValue());
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    BasicQuestion basicQuestion = modelAbstractFactory.createBasicQuestion();
                    basicQuestion.setQuestionId(resultSet.getInt(1));
                    basicQuestion.setQuestionTitle(resultSet.getString(2));
                    basicQuestion.setQuestionText(resultSet.getString(3));
                    basicQuestion.setQuestionType(QuestionType.valueOf(resultSet.getString(4)));

                    if (basicQuestion.getQuestionType() == QuestionType.NUMERIC
                            || basicQuestion.getQuestionType() == QuestionType.FREETEXT) {
                        surveyQuestion.setQuestionDetail(basicQuestion);
                    } else {
                        List<Option> optionList = new ArrayList<Option>();
                        MultipleChoiceQuestion multipleChoiceQuestion = modelAbstractFactory.createMultipleChoiceQuestion();
                        multipleChoiceQuestion.setQuestionId(basicQuestion.getQuestionId());
                        multipleChoiceQuestion.setQuestionType(basicQuestion.getQuestionType());
                        multipleChoiceQuestion.setQuestionText(basicQuestion.getQuestionText());
                        multipleChoiceQuestion.setQuestionTitle(basicQuestion.getQuestionTitle());
                        statement_options = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_multiplechoice") + "}");
                        statement_options.setInt(1, entry.getValue());
                        resultSet1 = statement_options.executeQuery();

                        while (resultSet1.next()) {
                            optionList.add(new Option(resultSet1.getString("option_text"), resultSet1.getInt("option_value")));
                        }
                        multipleChoiceQuestion.setOptionList(optionList);
                        surveyQuestion.setQuestionDetail(multipleChoiceQuestion);
                    }
                }
                surveyQuestionResponse.setSurveyQuestion(surveyQuestion);
                listOfQuestions.add(surveyQuestionResponse);
            }
            LOGGER.info("All the survey question fetched successfully");
            return listOfQuestions;
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("SQL exception generated while fetching questions of the survey " + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL exception generated while fetching questions of the survey " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic exception generated while fetching questions of the survey " + e.getLocalizedMessage());
            throw new Exception("Generic exception generated while fetching questions of the survey " + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            if (null != statement_options) {
                DBUtil.terminateStatement(statement_options);
            }
            DBUtil.terminateConnection();
        }

    }

    public Boolean checkPublished(int courseid) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving survey is published or not");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.check_published") + "}");
            statement.setInt(1, courseid);
            LOGGER.info("Querying Database to retrieve survey is published or not");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    LOGGER.info("Survey for the course" + courseid + " is published.");
                    return true;
                } else {
                    LOGGER.info("Survey for the course" + courseid + " is not published.");
                    return false;
                }
            }
            return false;
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("SQL exception generated while checking survey is published or not " + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL exception generated while checking survey is published or not " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic exception generated while checking survey is published or not " + e.getLocalizedMessage());
            throw new Exception("Generic exception generated while checking survey is published or not " + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
    }

    public Boolean checkSubmitted(String bannerid, int courseid) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving survey is submitted or not");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.check_submitted") + "}");
            statement.setString(1, bannerid);
            statement.setInt(2, courseid);
            LOGGER.info("Querying Database to retrieve survey is submitted or not");
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                LOGGER.info("Survey is not submitted for the student:" + bannerid);
                return false;
            }
            LOGGER.info("Survey is submitted for the student:" + bannerid);
            return true;
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("SQL exception generated while checking survey is submitted or not " + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL exception generated while  checking survey is submitted or not" + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
    }

    @Override
    public void createResponseId(int surveyQuestionId, String bannerId, Date responseDate, boolean submitted, int courseid) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Querying to create response id for the survey question in the database.");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.createresponseid") + "}");
            statement.setInt(1, surveyQuestionId);
            statement.setString(2, bannerId);
            statement.setDate(3, (java.sql.Date) responseDate);
            statement.setBoolean(4, submitted);
            statement.setInt(5, courseid);
            LOGGER.info("Querying Database to create response id for the question");
            resultSet = statement.executeQuery();
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("SQL exception generated while creating response id for the survey question " + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL exception generated while creating response id for the survey question " + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
    }

    @Override
    public void insertResponse(int surveyQuestionId, String bannerId, List<Object> responses) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Storing responses to the database.");
            for (Object response : responses) {
                statement = connection.prepareCall("{call " + properties.getProperty("procedure.insertresponse") + "}");
                statement.setInt(1, surveyQuestionId);
                statement.setString(2, bannerId);
                statement.setObject(3, response);
                resultSet = statement.executeQuery();
            }
            LOGGER.info("Successfully stored responses to the database.");
        } catch (UserDefinedSQLException e) {
            LOGGER.warning("SQL Exception generated while storing the responses to the database.");
            throw new UserDefinedSQLException("SQL Exception generated while storing the responses to the database.");
        } catch (Exception e) {
            LOGGER.warning(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
    }
}
