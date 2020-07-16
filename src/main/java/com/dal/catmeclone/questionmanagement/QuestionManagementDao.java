package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.User;

import java.util.List;

public interface QuestionManagementDao {

    public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedException;

    public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedException;

    public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceChoose)
            throws UserDefinedException;

    public List<BasicQuestion> getAllQuestionByUser(User u) throws UserDefinedException;

    public boolean deleteQuestion(int questionId) throws UserDefinedException;

}
