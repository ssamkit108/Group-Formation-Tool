package com.dal.catmeclone.questionmanagement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.DBUtility.DatabaseConnectionImpl;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.User;

public class QuestionManagementDaoImpl implements QuestionManagementDao{

DataBaseConnection db;
	
	final Logger logger = LoggerFactory.getLogger(DatabaseConnectionImpl.class);
	
	private CallableStatement statement;
	private Connection con;
	private ResultSet rs;
	
	@Override
	public List<BasicQuestion> getAllQuestionByUser(User u) throws SQLException, UserDefinedSQLException {
			db = SystemConfig.instance().getDatabaseConnection();
			con = db.connect();
			List<BasicQuestion> li = new ArrayList<BasicQuestion>();
			try {
				statement = con.prepareCall("{CALL GetAllQuestionTitles(?)}");
				statement.setString(1, u.getBannerId());
				rs = statement.executeQuery();
				
				BasicQuestion ques = null;
				while(rs.next()) {
					ques = new BasicQuestion();
					ques.setQuestionTitle(rs.getString("question_title"));
					ques.setQuestionText(rs.getString("questionText"));
					ques.setDate(rs.getDate("date"));
					li.add(ques);
				}
				logger.info("Retrieved successfully from the database");
				}
				catch(Exception e) {
					logger.error("Unable to execute query to get all courses");
					throw new UserDefinedSQLException("Some Error occured in executing query");
				}
				finally
				{
					if (statement != null)
					{
						statement.close();
					}
					if (con != null)
					{
						if (!con.isClosed())
						{
							con.close();
						}
					}
				}
				return li;
		}

	@Override
	public List<BasicQuestion> getSortedQuestionsByTitle(User u) throws SQLException, UserDefinedSQLException {
		db = SystemConfig.instance().getDatabaseConnection();
		con = db.connect();
		List<BasicQuestion> li = new ArrayList<BasicQuestion>();
		try {
			statement = con.prepareCall("{CALL GetAllQuestionTitles(?)}");
			statement.setString(1, u.getBannerId());
			rs = statement.executeQuery();
			
			BasicQuestion ques = null;
			while(rs.next()) {
				ques = new BasicQuestion();
				ques.setQuestionTitle(rs.getString("question_title"));
				ques.setQuestionText(rs.getString("questionText"));
				ques.setDate(rs.getDate("date"));
				li.add(ques);
			}
			logger.info("Retrieved successfully from the database");
			}
			catch(Exception e) {
				logger.error("Unable to execute query to get all courses");
				throw new UserDefinedSQLException("Some Error occured in executig query");
			}
			finally
			{
				if (statement != null)
				{
					statement.close();
				}
				if (con != null)
				{
					if (!con.isClosed())
					{
						con.close();
					}
				}
			}
		
		Collections.sort(li, new Comparator<BasicQuestion>() {
			  public int compare(BasicQuestion o1, BasicQuestion o2) {
			      return o1.getQuestionTitle().compareTo(o2.getQuestionTitle());
			  }
			});
		
		Collections.reverse(li);
		return li;		
	}

	@Override
	public List<BasicQuestion> getSortedQuestionsByDate(User u) throws SQLException, UserDefinedSQLException {
		db = SystemConfig.instance().getDatabaseConnection();
		con = db.connect();
		List<BasicQuestion> li = new ArrayList<BasicQuestion>();
		try {
			statement = con.prepareCall("{CALL GetAllQuestionTitles(?)}");
			statement.setString(1, u.getBannerId());
			rs = statement.executeQuery();
			
			BasicQuestion ques = null;
			while(rs.next()) {
				ques = new BasicQuestion();
				ques.setQuestionTitle(rs.getString("question_title"));
				ques.setQuestionText(rs.getString("questionText"));
				ques.setDate(rs.getDate("date"));
				li.add(ques);
			}
			logger.info("Retrieved successfully from the database");
			}
			catch(Exception e) {
				logger.error("Unable to execute query to get all courses");
				throw new UserDefinedSQLException("Some Error occured in executing query");
			}
			finally
			{
				if (statement != null)
				{
					statement.close();
				}
				if (con != null)
				{
					if (!con.isClosed())
					{
						con.close();
					}
				}
			}
		
		Collections.sort(li, new Comparator<BasicQuestion>() {
			  public int compare(BasicQuestion o1, BasicQuestion o2) {
			      if (o1.getDate() == null || o2.getDate() == null)
			        return 0;
			      return o1.getDate().compareTo(o2.getDate());
			  }
			});
		
		return li;
	}
	
	@Override
	public boolean deleteQuestionTitle(User u, BasicQuestion q) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
				// Connect to database
				db = SystemConfig.instance().getDatabaseConnection();
						con = db.connect();
						statement = con.prepareCall("{CALL deleteQuestions(?,?,?)}");
						
						try {
							statement.setString(1, q.getQuestionTitle() );
							statement.setString(2, u.getBannerId());
							statement.setString(3, q.getQuestionText() );
							statement.execute();
							logger.info("Question title:"+q.getQuestionTitle()+"Deleted successfully from the database");
						}
						catch(Exception e) {
							
							logger.error("Unable to execute query to delete course");
							return false;
						}
						finally
						{
							if (statement != null)
							{
								statement.close();
							}
							if (con != null)
							{
								if (!con.isClosed())
								{
									con.close();
								}
							}
						}
						return true;
	}
}
