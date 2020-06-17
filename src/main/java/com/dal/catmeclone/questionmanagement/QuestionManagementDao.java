package com.dal.catmeclone.questionmanagement;

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

}
