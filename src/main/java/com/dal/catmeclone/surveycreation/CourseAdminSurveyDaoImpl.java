package com.dal.catmeclone.surveycreation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;

public class CourseAdminSurveyDaoImpl implements CourseAdminSurveyDao {

	private Logger LOGGER = Logger.getLogger(CourseAdminSurveyDaoImpl.class.getName());

	private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	private DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	private ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
	private Properties properties = SystemConfig.instance().getProperties();

	private Connection connection;

	@Override
	public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();

		CallableStatement getSurveyStatement = null;
		CallableStatement getSurveyQuestionsStatement = null;
		Survey survey = null;

		try {
			connection = databaseUtil.connect();

			getSurveyStatement = connection.prepareCall("{call " + properties.getProperty("procedure.GetSurvey") + "}");
			getSurveyStatement.setInt(1, course.getCourseID());

			LOGGER.info("Querying Database to fetch list of survey for the course");
			ResultSet surveyResultSet = getSurveyStatement.executeQuery();

			// Checking if result set have any returned value
			while (surveyResultSet.next()) {

				// Assigning the fetched row into Survey object
				survey = modelAbstractFactory.createSurvey();
				survey.setSurveyId(surveyResultSet.getInt(1));
				survey.setPublishedStatus(surveyResultSet.getBoolean(2));
				survey.setGroupSize(surveyResultSet.getInt(3));
				survey.setGroupFormed(surveyResultSet.getBoolean(4));

				survey.setCourse(course);

				List<SurveyQuestion> listofSurveyQuestions = new ArrayList<SurveyQuestion>();

				getSurveyQuestionsStatement = connection
						.prepareCall("{call " + properties.getProperty("procedure.getQuestionsForSurvey") + "}");
				getSurveyQuestionsStatement.setInt(1, survey.getSurveyId());

				LOGGER.info("Querying Database to fetch list of surveyQuestion for the survey");
				ResultSet surveyQuestionResultSet = getSurveyQuestionsStatement.executeQuery();

				while (surveyQuestionResultSet.next()) {

					// Assigning the fetched row into Survey object
					BasicQuestion question = new BasicQuestion(surveyQuestionResultSet.getInt(2),
							surveyQuestionResultSet.getString(3), surveyQuestionResultSet.getString(4),
							surveyQuestionResultSet.getString(5));
					SurveyQuestion surveyQuestion = modelAbstractFactory.createSurveyQuestion(
							surveyQuestionResultSet.getInt(1), question, surveyQuestionResultSet.getString(6),
							surveyQuestionResultSet.getInt(7));
					listofSurveyQuestions.add(surveyQuestion);
				}
				survey.setSurveyQuestions(listofSurveyQuestions);

			}

			LOGGER.info("Retrieved successfully from the database");

		} catch (SQLException e) {
			LOGGER.warning("Error occured" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured while Accessing Resource. Please try again later");
		} finally {
			if (null != getSurveyStatement) {
				databaseUtil.terminateStatement(getSurveyStatement);
			}
			if (null != getSurveyQuestionsStatement) {
				databaseUtil.terminateStatement(getSurveyQuestionsStatement);
			}
			databaseUtil.terminateConnection();
		}
		return survey;
	}

	@Override
	public boolean createSurveyDetails(Survey survey) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();

		try {
			connection = databaseUtil.connect();
			connection.setAutoCommit(false);

			int surveyId = createNewSurvey(survey);
			survey.setSurveyId(surveyId);

			for (SurveyQuestion question : survey.getSurveyQuestions()) {
				createNewSurveyQuestion(question, surveyId);
			}
			// Performing commit once creation of all survey questions are completed.
			connection.commit();

		} catch (SQLException e) {

			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured while Accessing Resource. Please try again later");
		} finally {
			databaseUtil.terminateConnection();
		}
		return true;
	}

	public int createNewSurvey(Survey survey) throws UserDefinedException {

		int surveyId = 0;
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyCreateStatement = null;

		try {
			surveyCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.createSurvey") + "}");
			surveyCreateStatement.setInt(1, survey.getCourse().getCourseID());
			surveyCreateStatement.setBoolean(2, survey.isPublishedStatus());
			surveyCreateStatement.setInt(3, survey.getGroupSize());

			// Registering the output parameter
			surveyCreateStatement.registerOutParameter(4, Types.INTEGER);

			LOGGER.info("Calling Store procedure to execute query to create new survey");
			surveyCreateStatement.execute();

			// getting the output parameter from create procedure
			surveyId = surveyCreateStatement.getInt(4);

			LOGGER.info("Survey :" + surveyId + " saved in the database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Occured while Accessing Resource. Please try again later");
		} finally {
			if (null != surveyCreateStatement) {
				databaseUtil.terminateStatement(surveyCreateStatement);
			}
		}
		return surveyId;
	}

	public void createNewSurveyQuestion(SurveyQuestion question, int surveyId) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionCreateStatement = null;

		try {
			surveyQuestionCreateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.createSurveyQuestion") + "}");
			surveyQuestionCreateStatement.setInt(1, question.getQuestionDetail().getQuestionId());
			surveyQuestionCreateStatement.setInt(2, surveyId);
			surveyQuestionCreateStatement.setString(3, question.getAlgorithmLogicType());
			surveyQuestionCreateStatement.setString(4, question.getQuestionDetail().getQuestionType().toString());
			surveyQuestionCreateStatement.setInt(5, question.getLogicConstraintValue());

			// Registering the output parameter
			surveyQuestionCreateStatement.registerOutParameter(6, Types.INTEGER);

			LOGGER.info("Calling Store procedure to execute query and create question");
			surveyQuestionCreateStatement.execute();

			// getting the output parameter from create procedure
			int surveyquestionId = surveyQuestionCreateStatement.getInt(6);

			LOGGER.info("Survey Question:" + surveyquestionId + " saved in the database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != surveyQuestionCreateStatement) {
				databaseUtil.terminateStatement(surveyQuestionCreateStatement);
			}
		}
	}

	public void updateSurveyQuestion(SurveyQuestion question, int surveyId) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionUpdateStatement = null;

		try {
			surveyQuestionUpdateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.updateSurveyQuestion") + "}");

			surveyQuestionUpdateStatement.setString(1, question.getAlgorithmLogicType());
			surveyQuestionUpdateStatement.setString(2, question.getQuestionDetail().getQuestionType().toString());
			surveyQuestionUpdateStatement.setInt(3, question.getLogicConstraintValue());
			surveyQuestionUpdateStatement.setInt(4, question.getSurveyQuestionId());

			LOGGER.info("Calling Store procedure to execute query and create question");
			surveyQuestionUpdateStatement.execute();

			LOGGER.info("Survey Question:" + question.getSurveyQuestionId() + " updated in the database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != surveyQuestionUpdateStatement) {
				databaseUtil.terminateStatement(surveyQuestionUpdateStatement);
			}
		}
	}

	public void deleteSurveyQuestion(List<SurveyQuestion> surveyQuestionsToBeRemoved) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionDeleteStatement = null;

		try {
			surveyQuestionDeleteStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.deleteSurveyQuestion") + "}");
			for (SurveyQuestion question : surveyQuestionsToBeRemoved) {
				surveyQuestionDeleteStatement.setInt(1, question.getSurveyQuestionId());

				surveyQuestionDeleteStatement.execute();
				LOGGER.info(
						"Survey Question:" + question.getSurveyQuestionId() + " deleted from database successfully.");
			}

		} catch (SQLException e) {

			LOGGER.warning("Error Encountered while deleting survey question:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != surveyQuestionDeleteStatement) {
				databaseUtil.terminateStatement(surveyQuestionDeleteStatement);
			}
		}
	}

	public void updateSurvey(Survey survey) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionUpdateStatement = null;

		try {

			surveyQuestionUpdateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.updateSurvey") + "}");

			surveyQuestionUpdateStatement.setInt(1, survey.getGroupSize());
			surveyQuestionUpdateStatement.setInt(2, survey.getSurveyId());

			surveyQuestionUpdateStatement.execute();
			LOGGER.info("Survey Question:" + survey.getSurveyId() + " updated in database successfully.");

		} catch (SQLException e) {

			LOGGER.warning("Error Encountered while updating the survey question details:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {

			if (null != surveyQuestionUpdateStatement) {
				databaseUtil.terminateStatement(surveyQuestionUpdateStatement);
			}
		}
	}

	@Override
	public boolean updateSurveyDetails(Survey survey, List<SurveyQuestion> surveyQuestionsToBeRemoved)
			throws UserDefinedException {
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();

		try {
			connection = databaseUtil.connect();
			// Setting autocommit as false
			connection.setAutoCommit(false);

			updateSurvey(survey);

			for (SurveyQuestion question : survey.getSurveyQuestions()) {
				if (question.getSurveyQuestionId() != 0) {
					updateSurveyQuestion(question, survey.getSurveyId());
				} else {
					createNewSurveyQuestion(question, survey.getSurveyId());
				}
			}

			deleteSurveyQuestion(surveyQuestionsToBeRemoved);
			// performing commit only if all the update, creation and deletion process are
			// completed successfully.
			connection.commit();

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the connection
			databaseUtil.terminateConnection();
		}
		return true;
	}

	@Override
	public boolean publishSurvey(int surveyId) throws UserDefinedException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surevyUpdateStatement = null;

		try {
			connection = databaseUtil.connect();
			surevyUpdateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.publishsurvey") + "}");
			surevyUpdateStatement.setInt(1, surveyId);

			surevyUpdateStatement.execute();
			LOGGER.info("Survey Question:" + surveyId + " updated in database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != surevyUpdateStatement) {
				databaseUtil.terminateStatement(surevyUpdateStatement);
			}
			databaseUtil.terminateConnection();
		}
		return true;
	}

}
