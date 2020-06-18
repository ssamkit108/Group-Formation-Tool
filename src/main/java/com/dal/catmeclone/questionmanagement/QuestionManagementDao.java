package com.dal.catmeclone.questionmanagement;

import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;

public interface QuestionManagementDao {

	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException;

	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException;

	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceChoose)
			throws UserDefinedSQLException;

	public List<BasicQuestion> getAllQuestionByUser(User u) throws  UserDefinedSQLException;

	public boolean deleteQuestion(int questionId) throws UserDefinedSQLException;

}
