package com.dal.catmeclone.questionmanagement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	Logger logger = Logger.getLogger(QuestionManagementDaoImpl.class.getName());

	private DataBaseConnection dbUtil;

	private Connection connection;

	@Override
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException {
		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;
		ResultSet result = null;
		boolean isQuestionTitleExists = false;

		try {
			connection = dbUtil.connect();
			statement = connection.prepareCall("{call " + properties.getProperty("procedure.checkForQuestion") + "}");
			statement.setString(1, basicQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2, basicQuestion.getQuestionTitle());
			statement.setString(3, basicQuestion.getQuestionText());

			result = statement.executeQuery();

			if (result.next()) {
				isQuestionTitleExists = true;
			}
		} catch (SQLException e) {
			logger.info("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {

			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();

		}

		return isQuestionTitleExists;
	}

	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException {
		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;

		try {
			connection = dbUtil.connect();

			statement = connection.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
			statement.setString(1, textOrNumericQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2, textOrNumericQuestion.getQuestionTitle());
			statement.setString(3, textOrNumericQuestion.getQuestionText());
			statement.setString(4, textOrNumericQuestion.getQuestionType().toString());
			statement.setDate(5, new java.sql.Date(textOrNumericQuestion.getCreationDate().getTime()));

			logger.info("Calling Store procedure to execute query and create question");
			statement.execute();
			logger.info(
					"Question :" + textOrNumericQuestion.getQuestionText() + " Inserted in the database successfully.");

		} catch (SQLException e) {
			logger.info("Error Encountered:" + e.getLocalizedMessage());
			e.printStackTrace();
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {

			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();

		}

		return true;
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion)
			throws UserDefinedSQLException {

		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement questionCreateStatement = null, optionCreateStatement = null;

		try {
			connection = dbUtil.connect();
			// Making auto commit as false
			connection.setAutoCommit(false);

			System.out.println(multipleChoiceQuestion.getQuestionType().toString());
			questionCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.createQuestion") + "}");
			questionCreateStatement.setString(1, multipleChoiceQuestion.getCreatedByInstructor().getBannerId());
			questionCreateStatement.setString(2, multipleChoiceQuestion.getQuestionTitle());
			questionCreateStatement.setString(3, multipleChoiceQuestion.getQuestionText());
			questionCreateStatement.setString(4, multipleChoiceQuestion.getQuestionType().toString());
			questionCreateStatement.setDate(5, new java.sql.Date(multipleChoiceQuestion.getCreationDate().getTime()));
			questionCreateStatement.registerOutParameter(6, Types.INTEGER);

			logger.info("Calling Store procedure to execute query and create question");
			questionCreateStatement.execute();
			int questionId = questionCreateStatement.getInt(6);

			logger.info("Question :" + multipleChoiceQuestion.getQuestionText()
					+ " inserted in the question taable successfully.");

			optionCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.insertOptionforQuestion") + "}");
			for (Option option : multipleChoiceQuestion.getOptionList()) {
				optionCreateStatement.setInt(1, questionId);
				optionCreateStatement.setString(2, option.getOptionText());
				optionCreateStatement.setInt(3, option.getOptionValue());
				optionCreateStatement.execute();
				logger.info("Option:" + option.getOptionText() + " for Question "
						+ multipleChoiceQuestion.getQuestionText() + " inserted in the option taable successfully.");
			}

			// If all the above statement are executed successfully and without any
			// error/exception, Perform a commit
			connection.commit();
			logger.info(
					"Question :" + multipleChoiceQuestion.getQuestionText() + " created successfully with options.");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {

			if (questionCreateStatement != null) {
				dbUtil.terminateStatement(questionCreateStatement);
			}
			if (optionCreateStatement != null) {
				dbUtil.terminateStatement(optionCreateStatement);
			}
			dbUtil.terminateConnection();

		}

		return true;
	}

	@Override
	public List<BasicQuestion> getAllQuestionByUser(User u) throws UserDefinedSQLException {
		dbUtil = SystemConfig.instance().getDatabaseConnection();
		connection = dbUtil.connect();
		List<BasicQuestion> listOfQuestion = new ArrayList<BasicQuestion>();
		CallableStatement statement = null;

		try {
			logger.info("Retrieving from database");
			statement = connection.prepareCall("{CALL GetAllQuestionTitles(?)}");

			statement.setString(1, u.getBannerId());
			ResultSet rs = statement.executeQuery();
			BasicQuestion question = null;
			while (rs.next()) {
				question = new BasicQuestion();

				question.setQuestionTitle(rs.getString("question_title"));
				question.setQuestionText(rs.getString("questionText"));
				question.setQuestionType(QuestionType.valueOf(rs.getString("questionType")));
				question.setQuestionId(rs.getInt("questionId"));
				question.setCreationDate(rs.getDate("createddate"));

				listOfQuestion.add(question);
			}
			logger.info("Retrieved successfully from the database");
		} catch (SQLException e) {
			logger.info("error occured" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Some Error occured in executig query");
		} finally {
			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();
		}

		return listOfQuestion;
	}

	@Override
	public List<BasicQuestion> getSortedQuestionsByTitle(User u) throws UserDefinedSQLException {
		dbUtil = SystemConfig.instance().getDatabaseConnection();
		connection = dbUtil.connect();
		List<BasicQuestion> listOfQuestion = new ArrayList<BasicQuestion>();
		logger.info("Retrieved successfully from the database");
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{CALL GetAllQuestionTitles(?)}");
			statement.setString(1, u.getBannerId());
			ResultSet resultSet = statement.executeQuery();

			BasicQuestion question = null;
			while (resultSet.next()) {
				question = new BasicQuestion();
				question.setQuestionTitle(resultSet.getString("question_title"));
				question.setQuestionText(resultSet.getString("questionText"));
				question.setCreationDate(resultSet.getDate("createddate"));
				listOfQuestion.add(question);
			}
			logger.info("Retrieved successfully from the database");
		} catch (Exception e) {
			logger.info("Unable to execute query to get all courses");
			throw new UserDefinedSQLException("Some Error occured in executig query");
		} finally {
			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();
		}

		Collections.sort(listOfQuestion, new Comparator<BasicQuestion>() {
			public int compare(BasicQuestion o1, BasicQuestion o2) {
				return o1.getQuestionTitle().toLowerCase().compareTo(o2.getQuestionTitle().toLowerCase());
			}
		});

		return listOfQuestion;
	}

	@Override
	public List<BasicQuestion> getSortedQuestionsByDate(User u) throws SQLException, UserDefinedSQLException {
		dbUtil = SystemConfig.instance().getDatabaseConnection();
		connection = dbUtil.connect();
		List<BasicQuestion> listOfQuestion = new ArrayList<BasicQuestion>();
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{CALL GetAllQuestionTitles(?)}");
			statement.setString(1, u.getBannerId());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				BasicQuestion question = new BasicQuestion();
				question.setQuestionTitle(resultSet.getString("question_title"));
				question.setQuestionText(resultSet.getString("questionText"));
				question.setCreationDate(resultSet.getDate("createddate"));
				listOfQuestion.add(question);
			}
			logger.info("Retrieved successfully from the database");
		} catch (Exception e) {
			logger.info("Unable to execute query to get all question");
			throw new UserDefinedSQLException("Some Error occured in executing query");
		} finally {
			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();
		}

		Collections.sort(listOfQuestion, new Comparator<BasicQuestion>() {
			public int compare(BasicQuestion o1, BasicQuestion o2) {
				if (o1.getCreationDate() == null || o2.getCreationDate() == null)
					return 0;
				return o1.getCreationDate().compareTo(o2.getCreationDate());
			}
		});

		return listOfQuestion;
	}

	@Override
	public boolean deleteQuestion(int questionId) throws SQLException, UserDefinedSQLException {
		// Connect to database
		dbUtil = SystemConfig.instance().getDatabaseConnection();
		connection = dbUtil.connect();
		CallableStatement statement = connection.prepareCall("{CALL deleteQuestionsByID(?)}");
		try {
			statement.setInt(1, questionId);

			statement.execute();
			logger.info("Question with Id :" + questionId + "Deleted successfully from the database");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Unable to execute query to delete question");
			return false;
		} finally {
			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();
		}
		return true;
	}
}
