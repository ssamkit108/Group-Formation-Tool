package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class QuestionManagementDaoImpl implements QuestionManagementDao {

    Logger LOGGER = Logger.getLogger(QuestionManagementDaoImpl.class.getName());
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
    private DataBaseConnection DBUtil;
    private Connection connection;

    @Override
    public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException {

        DataBaseConnection DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        CallableStatement statement = null;
        ResultSet resultSet = null;
        boolean isQuestionTitleExists = false;

        try {
            connection = DBUtil.connect();

            statement = connection.prepareCall("{call " + properties.getProperty("procedure.checkForQuestion") + "}");
            statement.setString(1, basicQuestion.getCreatedByInstructor().getBannerId());
            statement.setString(2, basicQuestion.getQuestionTitle());
            statement.setString(3, basicQuestion.getQuestionText());

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LOGGER.info("Question with Given Title and Text exists");
                isQuestionTitleExists = true;
            }
        } catch (SQLException e) {
            LOGGER.warning("SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return isQuestionTitleExists;
    }

    @Override
    public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException {

        DataBaseConnection DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        CallableStatement statement = null;

        try {
            connection = DBUtil.connect();

            statement = connection.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
            statement.setString(1, textOrNumericQuestion.getCreatedByInstructor().getBannerId());
            statement.setString(2, textOrNumericQuestion.getQuestionTitle());
            statement.setString(3, textOrNumericQuestion.getQuestionText());
            statement.setString(4, textOrNumericQuestion.getQuestionType().toString());
            statement.setDate(5, new java.sql.Date(textOrNumericQuestion.getCreationDate().getTime()));
            statement.execute();

            LOGGER.info("Question :" + textOrNumericQuestion.getQuestionText() + " Inserted in the database successfully.");

        } catch (SQLException e) {
            LOGGER.warning(" SQL Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return true;
    }

    @Override
    public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
            throws UserDefinedSQLException {

        DataBaseConnection DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        CallableStatement questionCreateStatement = null, optionCreateStatement = null;

        try {
            connection = DBUtil.connect();
            connection.setAutoCommit(false);
            questionCreateStatement = connection.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
            questionCreateStatement.setString(1, multipleChoiceQuestion.getCreatedByInstructor().getBannerId());
            questionCreateStatement.setString(2, multipleChoiceQuestion.getQuestionTitle());
            questionCreateStatement.setString(3, multipleChoiceQuestion.getQuestionText());
            questionCreateStatement.setString(4, multipleChoiceQuestion.getQuestionType().toString());
            questionCreateStatement.setDate(5, new java.sql.Date(multipleChoiceQuestion.getCreationDate().getTime()));

            questionCreateStatement.registerOutParameter(6, Types.INTEGER);
            questionCreateStatement.execute();

            int questionId = questionCreateStatement.getInt(6);

            LOGGER.info("Question :" + multipleChoiceQuestion.getQuestionText()
                    + " inserted in the question taable successfully.");

            optionCreateStatement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.insertOptionforQuestion") + "}");

            for (Option option : multipleChoiceQuestion.getOptionList()) {
                optionCreateStatement.setInt(1, questionId);
                optionCreateStatement.setString(2, option.getOptionText());
                optionCreateStatement.setInt(3, option.getOptionValue());
                optionCreateStatement.execute();
                LOGGER.info("Option:" + option.getOptionText() + " for Question "
                        + multipleChoiceQuestion.getQuestionText() + " inserted in the option taable successfully.");
            }
            connection.commit();
            LOGGER.info(
                    "Question :" + multipleChoiceQuestion.getQuestionText() + " created successfully with options.");

        } catch (SQLException e) {
            LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
        } finally {
            if (questionCreateStatement != null) {
                DBUtil.terminateStatement(questionCreateStatement);
            }
            if (optionCreateStatement != null) {
                DBUtil.terminateStatement(optionCreateStatement);
            }
            DBUtil.terminateConnection();
        }
        return true;
    }

    @Override
    public List<BasicQuestion> getAllQuestionByUser(User u) throws UserDefinedSQLException {

        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        Properties properties = SystemConfig.instance().getProperties();
        List<BasicQuestion> listOfQuestion = new ArrayList<BasicQuestion>();
        CallableStatement statement = null;

        try {
            connection = DBUtil.connect();
            statement = connection
                    .prepareCall("{call " + properties.getProperty("procedure.getAllQuestionTitles") + "}");
            statement.setString(1, u.getBannerId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BasicQuestion question = new BasicQuestion();
                question.setQuestionTitle(resultSet.getString("question_title"));
                question.setQuestionText(resultSet.getString("questionText"));
                question.setQuestionType(QuestionType.valueOf(resultSet.getString("questionType")));
                question.setQuestionId(resultSet.getInt("questionId"));
                question.setCreationDate(resultSet.getDate("createddate"));
                listOfQuestion.add(question);
            }
        } catch (SQLException e) {
            LOGGER.warning("error occurred" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("Some Error occurred in executing query");
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return listOfQuestion;
    }

    @Override
    public boolean deleteQuestion(int questionId) throws UserDefinedSQLException {

        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        CallableStatement statement = null;
        try {
            connection = DBUtil.connect();
            Properties properties = SystemConfig.instance().getProperties();
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.deleteQuestionsByID") + "}");
            statement.setInt(1, questionId);
            statement.execute();
            LOGGER.info("Question with Id :" + questionId + "Deleted successfully from the database");
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("Error occured" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("Question is already used in any survey. Remove the Question from surevy before deleting it");
        } catch (SQLException e) {
            LOGGER.warning("Error occured" + e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL Error Occurred" + e.getLocalizedMessage());
        } finally {
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            DBUtil.terminateConnection();
        }
        return true;
    }

}
