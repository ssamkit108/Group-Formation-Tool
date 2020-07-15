package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface QuestionManagementService {

    public boolean createNumericOrTextQuestion(BasicQuestion basicQuestion) throws UserDefinedSQLException;

    public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoice) throws UserDefinedSQLException;

    public boolean ifQuestionTitleandTextExists(BasicQuestion basicQuestion) throws UserDefinedSQLException;

    public List<BasicQuestion> getAllQuestionByUser(User user) throws UserDefinedSQLException;

    public List<BasicQuestion> getSortedQuestionsByTitle(User user) throws UserDefinedSQLException;

    public List<BasicQuestion> getSortedQuestionsByDate(User user) throws UserDefinedSQLException;

    public boolean deleteQuestion(int questionId) throws UserDefinedSQLException;

}
