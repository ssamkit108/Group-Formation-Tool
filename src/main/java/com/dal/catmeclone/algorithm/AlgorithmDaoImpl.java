package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class AlgorithmDaoImpl implements AlgorithmDao {

    Logger LOGGER = Logger.getLogger(AlgorithmDaoImpl.class.getName());

    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    Properties properties = SystemConfig.instance().getProperties();
    ResultSet resultSet;
    private DataBaseConnection DBUtil;
    private Connection connection;

    @Override
    public List<String> getAllStudents(int courseid) throws UserDefinedException, Exception {

        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        List<String> surveySubmittedStudents = new ArrayList<String>();

        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getAllStudentsFromSurvey") + "}");
            statement.setInt(1, courseid);
            LOGGER.info("Querying Database to fetch list of question for the survey");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                surveySubmittedStudents.add(resultSet.getString("bannerid"));
            }
        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning("Generic Error Encountered" + e.getLocalizedMessage());
            throw new Exception("Generic Error Encountered" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return surveySubmittedStudents;
    }

    @Override
    public List<SurveyQuestionResponse> getAllResponsesOfAStudent(String bannerid, List<SurveyQuestion> listOfSurveyQuestions) throws UserDefinedException, Exception {

        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        List<SurveyQuestionResponse> userResponses = new ArrayList<SurveyQuestionResponse>();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getResponsesOfAStudentForQuestion") + "}");
            for (SurveyQuestion surveyQuestion : listOfSurveyQuestions) {
                statement.setString(1, bannerid);
                statement.setInt(2, surveyQuestion.getSurveyQuestionId());
                LOGGER.info("Querying Database to fetch list of response for certain survey question");
                resultSet = statement.executeQuery();
                ArrayList<Object> responsesForQuestion = new ArrayList<Object>();
                if (surveyQuestion.getQuestionDetail().getQuestionType() == QuestionType.FREETEXT) {
                    if (resultSet.next()) {
                        responsesForQuestion.add(resultSet.getString(1));
                    }
                } else {
                    while (resultSet.next()) {
                        responsesForQuestion.add(resultSet.getInt(1));
                    }
                }
                SurveyQuestionResponse response = new SurveyQuestionResponse(surveyQuestion, responsesForQuestion);
                userResponses.add(response);
            }
        } catch (SQLException e) {
            LOGGER.warning(" SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException(" SQL Error Encountered:" + e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.warning(" Generic Error Encountered" + e.getLocalizedMessage());
            throw new Exception("Generic Error Encountered" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return userResponses;
    }

    @Override
    public List<SurveyQuestion> getSurveyQuestionsForCourse(int courseid) throws UserDefinedException, Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        List<SurveyQuestion> listOfsurveyQuestion = new ArrayList<SurveyQuestion>();

        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getSurveyQuestionsForCourseId") + "}");
            statement.setInt(1, courseid);
            LOGGER.info("Querying Database to fetch list of question for the survey");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BasicQuestion questionDetail = new BasicQuestion(resultSet.getInt(4), QuestionType.valueOf(resultSet.getString(5)));
                SurveyQuestion surveyQuestion = new SurveyQuestion(questionDetail, resultSet.getString(2), resultSet.getInt(3));
                surveyQuestion.setSurveyQuestionId(resultSet.getInt(1));
                listOfsurveyQuestion.add(surveyQuestion);
            }

        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return listOfsurveyQuestion;
    }

    @Override
    public int getGroupSizeForCourse(int courseid) throws UserDefinedException {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();

        int groupSize;

        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getGroupSizeForCourse") + "}");
            statement.setInt(1, courseid);
            LOGGER.info("Querying Database to fetch list of question for the survey");

            resultSet = statement.executeQuery();
            resultSet.next();
            groupSize = resultSet.getInt(1);

        } catch (SQLException e) {
            LOGGER.warning(" SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return groupSize;
    }

    @Override
    public Boolean updateGroupsFormed(List<List<String>> groups, int courseId)
            throws UserDefinedException {
        DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        List<String> currentGroup;

        try {
            connection = databaseUtil.connect();
            connection.setAutoCommit(false);

            deleteGroups(courseId);

            for (int i = 0; i < groups.size(); i++) {
                currentGroup = new ArrayList<String>();
                currentGroup = groups.get(i);
                for (int j = 0; j < currentGroup.size(); j++) {
                    insertUserIntoGroup(currentGroup.get(j), courseId, Integer.toString(i + 1));
                }
            }

            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.setGroupFormedFlagforSurvey") + "}");

            statement.setInt(1, courseId);
            statement.setBoolean(2, true);

            statement.execute();
            LOGGER.info("Group Formed for the course and flag set successfully in database for the survey");

            connection.commit();

        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            databaseUtil.terminateConnection();
        }
        return true;
    }

    public void deleteGroups(int courseId) throws UserDefinedException {
        DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;

        try {
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.deleteGroupsForCourse") + "}");

            statement.setInt(1, courseId);

            LOGGER.info("Calling Store procedure to execute query and update question");
            statement.execute();
            LOGGER.info("Course Groups:" + courseId + " deleted successfully.");

        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                databaseUtil.terminateStatement(statement);
            }
        }
    }

    public void insertUserIntoGroup(String bannerid, int courseid, String groupname) throws UserDefinedException {
        DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;

        try {
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.insertIntoGroups") + "}");
            statement.setString(1, bannerid);
            statement.setInt(2, courseid);
            statement.setString(3, groupname);
            LOGGER.info("Calling Store procedure to execute query and insert bannerid into group_info");
            statement.execute();
            LOGGER.info("Bannerid " + bannerid + " inserted successfully.");

        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                databaseUtil.terminateStatement(statement);
            }
        }
    }
}
