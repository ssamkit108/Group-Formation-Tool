package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;

public interface QuestionManagementService {

	public boolean createNumericOrTextQuestion(BasicQuestion basicQuestion) throws UserDefinedSQLException;
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoice) throws UserDefinedSQLException;
	
	public boolean ifQuestionTitleandTextExists(BasicQuestion basicQuestion) throws UserDefinedSQLException;
}
