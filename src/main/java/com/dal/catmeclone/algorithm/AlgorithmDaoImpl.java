package com.dal.catmeclone.algorithm;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
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

	Logger LOGGER = Logger.getLogger(ResponseDaoImpl.class.getName());

	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	Properties properties = SystemConfig.instance().getProperties();

	private DataBaseConnection DBUtil;
	private Connection connection;
	ResultSet rs;

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
			rs = statement.executeQuery();

			while (rs.next()) {
				surveySubmittedStudents.add(rs.getString("bannerid"));
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
	public List<String> getAllResponsesOfAStudent(String bannerid, int courseid) throws UserDefinedSQLException {

		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		List<String> responseList = new ArrayList<String>();

		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			LOGGER.info("Retrieving from database");
			statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.getAllResponsesOfAStudent") + "}");
			statement.setString(1, bannerid);
			statement.setInt(2, courseid);
			LOGGER.info("Querying Database to fetch list of question for the survey");
			rs = statement.executeQuery();

			while (rs.next()) {
				responseList.add(rs.getString("Answer"));
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
		return responseList;
	}

	@Override
	public List<String> getSurveyCriteria(int courseid) throws UserDefinedSQLException {
		DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		List<String> responseCriteria = new ArrayList<String>();

		CallableStatement statement = null;
		try {
			connection = DBUtil.connect();
			LOGGER.info("Retrieving from database");
			statement = connection
					.prepareCall("{call " + properties.getProperty("procedure.getSurveyQuestionsCriteria") + "}");
			statement.setInt(1, courseid);
			LOGGER.info("Querying Database to fetch list of question for the survey");
			rs = statement.executeQuery();

			while (rs.next()) {
				responseCriteria.add(rs.getString("algorithmType"));
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
		return responseCriteria;
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

			rs = statement.executeQuery();
			rs.next();
			groupSize = rs.getInt(1);

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
