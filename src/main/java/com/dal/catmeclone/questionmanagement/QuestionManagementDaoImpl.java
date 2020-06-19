package com.dal.catmeclone.questionmanagement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;

public class QuestionManagementDaoImpl implements QuestionManagementDao {

	Logger LOGGER = Logger.getLogger(QuestionManagementDaoImpl.class.getName());

	private DataBaseConnection DBUtil;
	private Connection connection;

	/**
	 * Method To Check if Question with Given Title and text and By same User Exists
	 * or Not
	 * 
	 * @Parameter : BasicQuestion
	 */
	@Override
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException {

		DataBaseConnection DBUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;
		ResultSet result = null;
		boolean isQuestionTitleExists = false;

		try {
			// Connecting to Database
			connection = DBUtil.connect();

			// Creating a CallableStatement - Store procedure
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.checkForQuestion") + "}");
			statement.setString(1, basicQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2, basicQuestion.getQuestionTitle());
			statement.setString(3, basicQuestion.getQuestionText());

			// Executing Store procedure
			LOGGER.info("Calling Store procedure to check if Question with given Title and text exists");
			result = statement.executeQuery();

			// Checking if result set have any returned value
			if (result.next()) {
				LOGGER.info("Question with Given Title and Text exists");
				isQuestionTitleExists = true;
			}
		} catch (SQLException e) {
			// Handling Exception and Throwing Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement and Connection
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return isQuestionTitleExists;
	}

	/**
	 * Method To Create Numeric or Free Text Question
	 * 
	 * @Parameter : BasicQuestion
	 */
	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException {

		DataBaseConnection DBUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;

		try {
			// Connecting to Database
			connection = DBUtil.connect();

			// Creating a CallableStatement
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
			statement.setString(1, textOrNumericQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2, textOrNumericQuestion.getQuestionTitle());
			statement.setString(3, textOrNumericQuestion.getQuestionText());
			statement.setString(4, textOrNumericQuestion.getQuestionType().toString());
			statement.setDate(5, new java.sql.Date(textOrNumericQuestion.getCreationDate().getTime()));
			// statement.setInt(6, questionId);

			LOGGER.info("Calling Store procedure to execute query and create question");
			// Executing Store Procedure
			statement.execute();

			LOGGER.info(
					"Question :" + textOrNumericQuestion.getQuestionText() + " Inserted in the database successfully.");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement and Connection
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return true;
	}

	/**
	 * Method To Create Multiple Choice Question
	 * 
	 * @Parameter : MultipleChoiceQuestion
	 */
	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
			throws UserDefinedSQLException {

		DataBaseConnection DBUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement questionCreateStatement = null, optionCreateStatement = null;

		try {
			// Connecting to Database
			connection = DBUtil.connect();

			/*
			 * Making auto commit as false So that roll back can be done on question table
			 * if error is encountered on Option table
			 */
			connection.setAutoCommit(false);

			/*
			 * Creating a CallableStatement - Store procedure for inserting into question
			 * table
			 */
			questionCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
			questionCreateStatement.setString(1, multipleChoiceQuestion.getCreatedByInstructor().getBannerId());
			questionCreateStatement.setString(2, multipleChoiceQuestion.getQuestionTitle());
			questionCreateStatement.setString(3, multipleChoiceQuestion.getQuestionText());
			questionCreateStatement.setString(4, multipleChoiceQuestion.getQuestionType().toString());
			questionCreateStatement.setDate(5, new java.sql.Date(multipleChoiceQuestion.getCreationDate().getTime()));

			// Registering the output parameter
			questionCreateStatement.registerOutParameter(6, Types.INTEGER);

			LOGGER.info("Calling Store procedure to execute query and create question");
			// Executing Store Procedure
			questionCreateStatement.execute();

			// getting the Id of Newly inserted row in question table
			int questionId = questionCreateStatement.getInt(6);

			LOGGER.info("Question :" + multipleChoiceQuestion.getQuestionText()
					+ " inserted in the question taable successfully.");

			/*
			 * Creating a CallableStatement - Store procedure for inserting into options
			 * table
			 */
			optionCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.insertOptionforQuestion") + "}");

			// Iterating through each Options
			for (Option option : multipleChoiceQuestion.getOptionList()) {

				// Setting up field in store procedure
				optionCreateStatement.setInt(1, questionId);
				optionCreateStatement.setString(2, option.getOptionText());
				optionCreateStatement.setInt(3, option.getOptionValue());

				// Executing store procedure
				optionCreateStatement.execute();
				LOGGER.info("Option:" + option.getOptionText() + " for Question "
						+ multipleChoiceQuestion.getQuestionText() + " inserted in the option taable successfully.");
			}

			/*
			 * If all the above statement are executed successfully and without any
			 * error/exception, perform a commit
			 */
			connection.commit();
			LOGGER.info(
					"Question :" + multipleChoiceQuestion.getQuestionText() + " created successfully with options.");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement and Connection
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

	/**
	 * Method To Get All Questions for the User
	 * 
	 * @Parameter : MultipleChoiceQuestion
	 */
	@Override
	public List<BasicQuestion> getAllQuestionByUser(User u) throws UserDefinedSQLException {

		DBUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		List<BasicQuestion> listOfQuestion = new ArrayList<BasicQuestion>();
		CallableStatement statement = null;

		try {
			// Connecting to Database
			connection = DBUtil.connect();
			LOGGER.info("Retrieving from database");

			/*
			 * Creating a CallableStatement - Store procedure for getting all questions for
			 * given user
			 */
			statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.getAllQuestionTitles") + "}");
			statement.setString(1, u.getBannerId());

			// Executing the Store Procedure
			LOGGER.info("Querying Database to fetch list of question for the user");
			ResultSet rs = statement.executeQuery();

			// Checking if result set have any returned value
			while (rs.next()) {

				// Assigning the fetched row into Basic Question object
				BasicQuestion question = new BasicQuestion();
				question.setQuestionTitle(rs.getString("question_title"));
				question.setQuestionText(rs.getString("questionText"));
				question.setQuestionType(QuestionType.valueOf(rs.getString("questionType")));
				question.setQuestionId(rs.getInt("questionId"));
				question.setCreationDate(rs.getDate("createddate"));

				// Adding the Question to the Question List
				listOfQuestion.add(question);
			}

			LOGGER.info("Retrieved successfully from the database");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("error occured" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Some Error occured in executig query");
		} finally {
			// Closing the Statement and Connection
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}

		return listOfQuestion;
	}

	/**
	 * Method To Delete Question with given Question Id
	 * 
	 * @Parameter : questionId
	 */
	@Override
	public boolean deleteQuestion(int questionId) throws UserDefinedSQLException {

		DBUtil = SystemConfig.instance().getDatabaseConnection();
		CallableStatement statement = null;
		try {
			// Connect to database
			connection = DBUtil.connect();

			/*
			 * Creating a CallableStatement - Store procedure to delete question and its
			 * related entity in options table
			 */
			statement = connection.prepareCall("{CALL deleteQuestionsByID(?)}");
			statement.setInt(1, questionId);

			// Executing the Store Procedure
			LOGGER.info("Updating Database by delete question for the user");
			statement.execute();

			LOGGER.info("Question with Id :" + questionId + "Deleted successfully from the database");
		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error occured" + e.getLocalizedMessage());
			return false;
		} finally {
			// Closing the Statement and Connection
			if (null != statement) {
				DBUtil.terminateStatement(statement);
			}
			DBUtil.terminateConnection();
		}
		return true;
	}

}
