package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.DuplicateEntityException;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;

public class QuestionManagementServiceImpl implements QuestionManagementService {

	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion basicQuestion) throws UserDefinedSQLException {

		QuestionManagementDao questionManagemetDao = SystemConfig.instance().getQuestionManagementDao();		
		boolean isQuestionCreated = questionManagemetDao.createNumericOrTextQuestion(basicQuestion);
		return isQuestionCreated;
		
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoice) throws UserDefinedSQLException{
		
		QuestionManagementDao questionManagemetDao = SystemConfig.instance().getQuestionManagementDao();
		multipleChoice.filterOptions();
		boolean	isQuestionCreated = questionManagemetDao.createMultipleChoiceQuestion(multipleChoice);
		return isQuestionCreated;
	}

	@Override
	public boolean ifQuestionTitleandTextExists(BasicQuestion basicQuestion) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		QuestionManagementDao questionManagemetDao = SystemConfig.instance().getQuestionManagementDao();
		boolean isQuestionExists = questionManagemetDao.isQuestionExistForUserWithTitleandText(basicQuestion);
		return isQuestionExists;
	}

}
