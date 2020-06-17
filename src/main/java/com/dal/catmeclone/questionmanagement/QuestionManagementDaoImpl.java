package com.dal.catmeclone.questionmanagement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Logger;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;


public class QuestionManagementDaoImpl implements QuestionManagementDao {

	Logger logger = Logger.getLogger(QuestionManagementDaoImpl.class.getName());
	
	private DataBaseConnection dbUtil;
	
	private Connection connection;

	@Override
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;
		ResultSet result=null;
		boolean isQuestionTitleExists = false;

		try {
			connection = dbUtil.connect();
			statement = connection.prepareCall("{call "+properties.getProperty("procedure.checkForQuestion")+"}");
			statement.setString(1,basicQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2,basicQuestion.getQuestionTitle());
			statement.setString(3,basicQuestion.getQuestionText());
			
			result = statement.executeQuery();
		
			if (result.next()) {
				isQuestionTitleExists = true;
			}
		} catch (SQLException e) {
			logger.info("Error Encountered:" +e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" +e.getLocalizedMessage());
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
		// TODO Auto-generated method stub
		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement statement = null;
		
		try {
			connection = dbUtil.connect();
			statement = connection.prepareCall("{call "+properties.getProperty("procedure.createquestion")+"}");
			statement.setString(1,textOrNumericQuestion.getCreatedByInstructor().getBannerId());
			statement.setString(2,textOrNumericQuestion.getQuestionTitle());
			statement.setString(3,textOrNumericQuestion.getQuestionText());
			statement.setString(4,textOrNumericQuestion.getQuestionType().toString());
			statement.setDate(4,new java.sql.Date(textOrNumericQuestion.getCreationDate().getTime()));
			
			logger.info("Calling Store procedure to execute query and create question");
			statement.execute();
			logger.info("Question :"+textOrNumericQuestion.getQuestionText()+" Inserted in the database successfully.");
			
		} catch (SQLException e) {
			logger.info("Error Encountered:" +e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" +e.getLocalizedMessage());
		} finally {

			if (statement != null) {
				dbUtil.terminateStatement(statement);
			}
			dbUtil.terminateConnection();
			
		}

		return true;
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		DataBaseConnection dbUtil = SystemConfig.instance().getDatabaseConnection();
		Properties properties = SystemConfig.instance().getProperties();
		CallableStatement questionCreateStatement=null, optionCreateStatement = null;

		
		try {
			connection = dbUtil.connect();
			//Making auto commit as false
			connection.setAutoCommit(false);
			
			questionCreateStatement = connection.prepareCall("{call "+properties.getProperty("procedure.createquestion")+"}");
			questionCreateStatement.setString(1,multipleChoiceQuestion.getCreatedByInstructor().getBannerId());
			questionCreateStatement.setString(2,multipleChoiceQuestion.getQuestionTitle());
			questionCreateStatement.setString(3,multipleChoiceQuestion.getQuestionText());
			questionCreateStatement.setString(4,multipleChoiceQuestion.getQuestionType().toString());
			questionCreateStatement.setDate(4,new java.sql.Date(multipleChoiceQuestion.getCreationDate().getTime()));
			
			logger.info("Calling Store procedure to execute query and create question");
			questionCreateStatement.execute();
			logger.info("Question :"+multipleChoiceQuestion.getQuestionText()+" inserted in the question taable successfully.");
			
			optionCreateStatement = connection.prepareCall("{call "+properties.getProperty("procedure.createquestion")+"}");
			for(Option option: multipleChoiceQuestion.getOptionList())
			{
				optionCreateStatement.setString(1, option.getOptionText());
				optionCreateStatement.setInt(2, option.getOptionValue());
				optionCreateStatement.execute();
				logger.info("Option:"+option.getOptionText()+" for Question "+multipleChoiceQuestion.getQuestionText()+" inserted in the option taable successfully.");
			}
			
			//If all the above statement are executed successfully and without any error/exception, Perform a commit
			connection.commit();
			logger.info("Question :"+multipleChoiceQuestion.getQuestionText()+" created successfully with options.");
			
			
		} catch (SQLException e) {
			logger.info("Error Encountered:" +e.getLocalizedMessage());
			throw new UserDefinedSQLException("Error Encountered:" +e.getLocalizedMessage());
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
	public int getQuestionId(BasicQuestion theBasicQuestionData) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

	

	

	/*@Override
	public int getIdForQuestionTitle(String questionTitle) {
		Connection connection = null;
		PreparedStatement thePreparedStatement = null;
		ResultSet theResultSet = null;
		int titleId = 0;
		try {
			connection = ConnectionManager.getInstance().getDBConnection();
			thePreparedStatement = connection.prepareStatement(DataBaseQueriesUtil.isQuestionTitle);
			thePreparedStatement.setString(1, questionTitle.toLowerCase());
			theResultSet = thePreparedStatement.executeQuery();
			if (theResultSet.next()) {
				titleId = theResultSet.getInt("qtitleid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (theResultSet != null) {
					theResultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (thePreparedStatement != null) {
					thePreparedStatement.close();
				}
				log.info("closing connection after getting title id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return titleId;
	}

	@Override
	public int getIdForQuestionType(String questionType) {

		Connection connection = null;
		PreparedStatement thePreparedStatement = null;
		ResultSet theResultSet = null;
		int typeId = 0;

		try {
			connection = ConnectionManager.getInstance().getDBConnection();
			thePreparedStatement = connection.prepareStatement(DataBaseQueriesUtil.getQuestionTypeId);
			thePreparedStatement.setString(1, questionType);
			theResultSet = thePreparedStatement.executeQuery();
			if (theResultSet.next()) {
				typeId = theResultSet.getInt("questiontypeid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (theResultSet != null) {
					theResultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (thePreparedStatement != null) {
					thePreparedStatement.close();
				}
				log.info("closing connection after getting title id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return typeId;
	}

	@Override
	public int getQuestionId(BasicQuestionData theBasicQuestionData) {
		Connection connection = null;
		PreparedStatement thePreparedStatement = null;
		ResultSet theResultSet = null;
		UserService theUserService = SystemConfig.getSingletonInstance().getTheUserService();
		int id = 0;
		try {
			connection = ConnectionManager.getInstance().getDBConnection();
			thePreparedStatement = connection.prepareStatement(DataBaseQueriesUtil.getQuestionId);
			thePreparedStatement.setString(1, theUserService.getCurrentUser().getBannerId());
			thePreparedStatement.setInt(2, this.getIdForQuestionType(theBasicQuestionData.getQuestionType()));
			thePreparedStatement.setInt(3, this.getIdForQuestionTitle(theBasicQuestionData.getQuestionTitle()));
			thePreparedStatement.setString(4, theBasicQuestionData.getQuestionText().toLowerCase());
			theResultSet = thePreparedStatement.executeQuery();
			if (theResultSet.next()) {
				id = theResultSet.getInt(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			try {
				if (theResultSet != null) {
					theResultSet.close();
				}
				if (connection != null) {
					connection.close();
				}
				if (thePreparedStatement != null) {
					thePreparedStatement.close();
				}
				log.info("closing connection after getting question id");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return id;
	}*/

	
}
