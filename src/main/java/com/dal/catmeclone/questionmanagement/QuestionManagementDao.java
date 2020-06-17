package com.dal.catmeclone.questionmanagement;

import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.User;

public interface QuestionManagementDao {

	public List<BasicQuestion> getAllQuestionByUser(User u) throws SQLException, UserDefinedSQLException;
	
	public List<BasicQuestion> getSortedQuestionsByTitle(User u) throws SQLException, UserDefinedSQLException;
	
	public List<BasicQuestion> getSortedQuestionsByDate(User u) throws SQLException, UserDefinedSQLException;
	
	public boolean deleteQuestionTitle(User u, BasicQuestion q) throws SQLException, UserDefinedSQLException;
}
