package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.surveyresponse.ResponseDaoImpl;

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

	private DataBaseConnection DBUtil;
	private Connection connection;
	ResultSet resultSet;

	// Should Refine this functions to another class
	@Override
	public List<String> getAllStudents(int courseid) throws UserDefinedSQLException {

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
			// Handling Exception and Throwing Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return surveySubmittedStudents;
	}

	@Override
	public List<SurveyQuestionResponse> getAllResponsesOfAStudent(String bannerid, List<SurveyQuestion> listOfSurveyQuestions) throws UserDefinedSQLException {

		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		List<SurveyQuestionResponse> userresponse = new ArrayList<SurveyQuestionResponse>();
	

		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			LOGGER.info("Retrieving from database");
			statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.getResponsesOfAStudentForQuestion") + "}");
			for(SurveyQuestion surveyQuestion: listOfSurveyQuestions)
			{
				statement.setString(1, bannerid);
				statement.setInt(2, surveyQuestion.getSurveyQuestionId());
				LOGGER.info("Querying Database to fetch list of response for certain survey question");
				resultSet = statement.executeQuery();
				ArrayList<Object> responsesForQuestion = new ArrayList<Object>();
				if(surveyQuestion.getQuestionDetail().getQuestionType()==QuestionType.FREETEXT) {
					if (resultSet.next()) {
						responsesForQuestion.add(resultSet.getString(1));
					}
				}
				else {
					while (resultSet.next()) {
						responsesForQuestion.add(resultSet.getInt(1));
					}
				}
				
				SurveyQuestionResponse response = new SurveyQuestionResponse(surveyQuestion, responsesForQuestion);
				userresponse.add(response);
			}
		} catch (SQLException e) {
			// Handling Exception and Throwing Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return userresponse;
	}

	@Override
	public List<SurveyQuestion> getSurveyQuestionsForCourse(int courseid) throws UserDefinedSQLException {
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
			// Handling Exception and Throwing Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return listOfsurveyQuestion;
	}

	@Override
	public int getGroupSizeForCourse(int courseid) throws UserDefinedSQLException {
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
			// Handling Exception and Throwing Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return groupSize;
	}

}
