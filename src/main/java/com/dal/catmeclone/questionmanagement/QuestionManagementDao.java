package com.dal.catmeclone.questionmanagement;


import java.sql.SQLException;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;


public interface QuestionManagementDao {
	
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException;
		
	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException ;
	
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceChoose) throws UserDefinedSQLException ;
	
	/*public int getIdForQuestionTitle(String questionTitle);
	
	public int getIdForQuestionType(String questionType);
	
	public int getQuestionId(BasicQuestion theBasicQuestionData);*/


	public List<BasicQuestion> getAllQuestionByUser(User u) throws SQLException, UserDefinedSQLException;
	
	public List<BasicQuestion> getSortedQuestionsByTitle(User u) throws SQLException, UserDefinedSQLException;
	
	public List<BasicQuestion> getSortedQuestionsByDate(User u) throws SQLException, UserDefinedSQLException;
	
	public boolean deleteQuestion(int questionId) throws SQLException, UserDefinedSQLException;

}
