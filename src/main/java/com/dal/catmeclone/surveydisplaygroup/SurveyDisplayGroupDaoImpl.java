package com.dal.catmeclone.surveydisplaygroup;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

public class SurveyDisplayGroupDaoImpl implements SurveyDisplayGroupDao {

	Logger LOGGER = Logger.getLogger(SurveyDisplayGroupDaoImpl.class.getName());

	private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	private DBUtilityAbstractFactory dbUtilityAbstractFactory = abstractFactory.createDBUtilityAbstractFactory();
	private Properties properties = SystemConfig.instance().getProperties();
	private Connection connection;
	
	@Override
	public HashMap<String, List<User>> retrievegroupinfo(int courseid) throws UserDefinedException {
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement GroupinfoResponse = null;
		HashMap<String, List<User>> groupData = new HashMap<>();

		try {
			connection = databaseUtil.connect();

			GroupinfoResponse = connection
					.prepareCall("{call " + properties.getProperty("procedure.display_groups") + "}");
			GroupinfoResponse.setInt(1, courseid);

			LOGGER.info("Querying Database to fetch groups formed for the course");
			ResultSet groupinforesult = GroupinfoResponse.executeQuery();
			while (groupinforesult.next()) {
				User user = abstractFactory.createModelAbstractFactory().createUser();
				user.setBannerId(groupinforesult.getString("bannerid"));
				user.setFirstName(groupinforesult.getString("firstname"));
				user.setLastName(groupinforesult.getString("lastname"));
				user.setEmail(groupinforesult.getString("email"));
				
				String groupName = groupinforesult.getString("group_name");
				
				if (groupData != null && groupData.containsKey(groupName)) {
					List<User> users = groupData.get(groupName);
					users.add(user);
				}
				else {
					List<User> userdata = new ArrayList<>();
					userdata.add(user);
					groupData.put(groupName, userdata);
				}
			}
			LOGGER.info("Group Info:" + courseid + " fetched data from database successfully.");
		} catch (SQLException e) {
			LOGGER.warning("Error Encountered:" + e.getLocalizedMessage());
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != GroupinfoResponse) {
				databaseUtil.terminateStatement(GroupinfoResponse);
			}
		}
	
		return groupData;
	}

	@Override
	public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedException {
		DataBaseConnection databaseUtil = dbUtilityAbstractFactory.createDataBaseConnection();
		CallableStatement questionResponse = null;
		CallableStatement getOptionStatement= null;
		HashMap<String,List<Object>> response = new HashMap<>();

		try {
			connection = databaseUtil.connect();

			questionResponse = connection
					.prepareCall("{call " + properties.getProperty("procedure.fetch_survey_responses") + "}");
			questionResponse.setInt(1, courseid);
			questionResponse.setString(2, bannerid);

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
			throw new UserDefinedException("Error Encountered:" + e.getLocalizedMessage());
		} finally {
			if (null != questionResponse) {
				databaseUtil.terminateStatement(questionResponse);
			}
		}
		return response;

	}
	
}
