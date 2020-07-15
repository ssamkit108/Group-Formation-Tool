package com.dal.catmeclone.surveycreation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.User;


public class CourseAdminSurveyDaoImpl implements CourseAdminSurveyDao {

	Logger LOGGER = Logger.getLogger(CourseAdminSurveyDaoImpl.class.getName());

	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();
	Properties properties = SystemConfig.instance().getProperties();

	private Connection connection;

	@Override
	public Survey getSurveyDetailsForCourse(Course course) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();

		CallableStatement getSurveyStatement = null;
		CallableStatement getSurveyQuestionsStatement = null;
		Survey survey = null;

		try {
			connection = databaseUtil.connect();
			LOGGER.info("Retrieving from database");

			// Creating a CallableStatement - Store procedure for getting survey for given course
			getSurveyStatement = connection.prepareCall("{call " + properties.getProperty("procedure.GetSurvey") + "}");
			getSurveyStatement.setInt(1, course.getCourseID());

			LOGGER.info("Querying Database to fetch list of survey for the course");
			ResultSet surveyResultSet = getSurveyStatement.executeQuery();

			// Checking if result set have any returned value
			while (surveyResultSet.next()) {

				// Assigning the fetched row into Survey object
				survey = new Survey();
				survey.setSurveyId(surveyResultSet.getInt(1));
				survey.setPublishedStatus(surveyResultSet.getBoolean(2));
				survey.setGroupSize(surveyResultSet.getInt(3));
				survey.setGroupFormed(surveyResultSet.getBoolean(4));

				survey.setCourse(course);

				List<SurveyQuestion> listofSurveyQuestions = new ArrayList<SurveyQuestion>();

				// Creating a CallableStatement - Store procedure for getting survey question details for fetched survey
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
					SurveyQuestion surveyQuestion = new SurveyQuestion(surveyQuestionResultSet.getInt(1), question,
							surveyQuestionResultSet.getString(6), surveyQuestionResultSet.getInt(7));
					listofSurveyQuestions.add(surveyQuestion);
				}
				survey.setSurveyQuestions(listofSurveyQuestions);

			}

			LOGGER.info("Retrieved successfully from the database");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("error occured" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Some Error occured in executig query");
		} finally {
			// Closing the Statement and Connection
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
	public boolean createSurveyDetails(Survey survey) throws UserDefinedSQLException {

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
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			databaseUtil.terminateConnection();
		}
		return true;
	}

	public int createNewSurvey(Survey survey) throws UserDefinedSQLException {

		int surveyId = 0;
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyCreateStatement = null;

		try {
			// setting a CallableStatement
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
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			if (null != surveyCreateStatement) {
				databaseUtil.terminateStatement(surveyCreateStatement);
			}
		}
		return surveyId;
	}

	public void createNewSurveyQuestion(SurveyQuestion question, int surveyId) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionCreateStatement = null;

		try {
			// setting a CallableStatement
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
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			if (null != surveyQuestionCreateStatement) {
				databaseUtil.terminateStatement(surveyQuestionCreateStatement);
			}
		}
	}

	public void updateSurveyQuestion(SurveyQuestion question, int surveyId) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionUpdateStatement = null;

		try {
			// setting a CallableStatement
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
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			if (null != surveyQuestionUpdateStatement) {
				databaseUtil.terminateStatement(surveyQuestionUpdateStatement);
			}
		}
	}

	public void deleteSurveyQuestion(List<SurveyQuestion> surveyQuestionsToBeRemoved) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionDeleteStatement = null;

		try {
			// setting a CallableStatement
			surveyQuestionDeleteStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.deleteSurveyQuestion") + "}");
			for (SurveyQuestion question : surveyQuestionsToBeRemoved) {
				surveyQuestionDeleteStatement.setInt(1, question.getSurveyQuestionId());
				LOGGER.info("Calling Store procedure to execute query and create question");
				surveyQuestionDeleteStatement.execute();
				LOGGER.info(
						"Survey Question:" + question.getSurveyQuestionId() + " deleted from database successfully.");
			}

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			if (null != surveyQuestionDeleteStatement) {
				databaseUtil.terminateStatement(surveyQuestionDeleteStatement);
			}
		}
	}

	public void updateSurvey(Survey survey) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surveyQuestionUpdateStatement = null;

		try {
			// setting a CallableStatement
			surveyQuestionUpdateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.updateSurvey") + "}");

			surveyQuestionUpdateStatement.setInt(1, survey.getGroupSize());
			surveyQuestionUpdateStatement.setInt(2, survey.getSurveyId());

			LOGGER.info("Calling Store procedure to execute query and update question");
			surveyQuestionUpdateStatement.execute();
			LOGGER.info("Survey Question:" + survey.getSurveyId() + " updated in database successfully.");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			if (null != surveyQuestionUpdateStatement) {
				databaseUtil.terminateStatement(surveyQuestionUpdateStatement);
			}
		}
	}

	@Override
	public boolean updateSurveyDetails(Survey survey, List<SurveyQuestion> surveyQuestionsToBeRemoved)
			throws UserDefinedSQLException {
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
			// performing commit only if all the update, creation and deletion process are completed successfully.
			connection.commit();

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement
			databaseUtil.terminateConnection();
		}
		return true;
	}

	@Override
	public boolean publishSurvey(int surveyId) throws UserDefinedSQLException {

		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement surevyUpdateStatement = null;

		try {
			connection = databaseUtil.connect();
			// setting a CallableStatement
			surevyUpdateStatement = connection
					.prepareCall("{call " + properties.getProperty("procedure.publishsurvey") + "}");
			surevyUpdateStatement.setInt(1, surveyId);

			LOGGER.info("Calling Store procedure to execute query and update question");
			surevyUpdateStatement.execute();
			LOGGER.info("Survey Question:" + surveyId + " updated in database successfully.");

		} catch (SQLException e) {
			// Handling Exception and Throwing User Defined Customized Exception
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			// Closing the Statement and Connection
			if (null != surevyUpdateStatement) {
				databaseUtil.terminateStatement(surevyUpdateStatement);
			}
			databaseUtil.terminateConnection();
		}

		return true;
	}

	@Override
	public HashMap<String, List<User>> retrievegroupinfo(int courseid) throws UserDefinedSQLException {
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement GroupinfoResp = null;
		HashMap<String, List<User>> grp_data = new HashMap<>();


		try {
			LOGGER.info("Retrieving from database");
			connection = databaseUtil.connect();

			GroupinfoResp = connection
					.prepareCall("{call " + properties.getProperty("procedure.display_groups") + "}");
			GroupinfoResp.setInt(1, courseid);

			LOGGER.info("Querying Database to fetch groups formed for the course");
			ResultSet groupinforesult = GroupinfoResp.executeQuery();
			while (groupinforesult.next()) {
				User usr = abstractFactory.createModelAbstractFactory().createUser();
				usr.setBannerId(groupinforesult.getString("bannerid"));
				usr.setFirstName(groupinforesult.getString("firstname"));
				usr.setLastName(groupinforesult.getString("lastname"));
				usr.setEmail(groupinforesult.getString("email"));
				
				String group_name = groupinforesult.getString("group_name");
				
				if (grp_data != null && grp_data.containsKey(group_name)) {
					List<User> users = grp_data.get(group_name);
					users.add(usr);
					
				}
				else {
					List<User> usrdata = new ArrayList<>();
					usrdata.add(usr);
					grp_data.put(group_name, usrdata);
				}
				
			}
			
			LOGGER.info("Group Info:" + courseid + " fetched data from database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != GroupinfoResp) {
				databaseUtil.terminateStatement(GroupinfoResp);
			}
		}
	
		return grp_data;
	}

	@Override
	public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedSQLException {
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement questionResponse = null;
		CallableStatement getOptionStatement= null;
		HashMap<String,List<Object>> response = new HashMap<>();

		try {
			LOGGER.info("Retrieving from database");
			connection = databaseUtil.connect();

			questionResponse = connection
					.prepareCall("{call " + properties.getProperty("procedure.fetch_survey_responses") + "}");
			questionResponse.setInt(1, courseid);
			questionResponse.setString(2, bannerid);

			LOGGER.info("Querying Database to fetch responses given by a student");
			ResultSet responseResultSet = questionResponse.executeQuery();
			
			while (responseResultSet.next()) {
				
				List<Object> answers=null;
				if (response != null && response.containsKey(responseResultSet.getString("questionText"))) {
					answers = response.get(responseResultSet.getString("questionText"));
				
				}
				else {
					answers = new ArrayList<>();
					response.put(responseResultSet.getString("questionText"), answers);
				}
				
				if(responseResultSet.getString("questionType").equals("MULTIPLECHOICEONE")||responseResultSet.getString("questionType").equals("MULTIPLECHOICEMANY"))
				{
					getOptionStatement= connection
							.prepareCall("{call " + properties.getProperty("procedure.getTextForOptionValues") + "}");
					getOptionStatement.setInt(1, responseResultSet.getInt("questionId"));
					getOptionStatement.setInt(2, Integer.parseInt(responseResultSet.getString("answer")));
					
					ResultSet optionResultSet = getOptionStatement.executeQuery();
					if(optionResultSet.next())
					{
						answers.add(optionResultSet.getString("option_text"));
					}
				}
				else
				{
					answers.add(responseResultSet.getString("answer"));
				}
			}
			
			LOGGER.info("Question Response:" + bannerid + " fetched data from database successfully.");

		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != questionResponse) {
				databaseUtil.terminateStatement(questionResponse);
			}
		}
		return response;

	}

}
