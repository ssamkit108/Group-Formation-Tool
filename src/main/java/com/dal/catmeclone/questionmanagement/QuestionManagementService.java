package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface QuestionManagementService {

    public boolean createNumericOrTextQuestion(BasicQuestion basicQuestion) throws UserDefinedException;

    public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoice) throws UserDefinedException;

    public boolean ifQuestionTitleandTextExists(BasicQuestion basicQuestion) throws UserDefinedException;

    public List<BasicQuestion> getAllQuestionByUser(User user) throws UserDefinedException;

    public List<BasicQuestion> getSortedQuestionsByTitle(User user) throws UserDefinedException;

    public List<BasicQuestion> getSortedQuestionsByDate(User user) throws UserDefinedException;

    public boolean deleteQuestion(int questionId) throws UserDefinedException;

}
