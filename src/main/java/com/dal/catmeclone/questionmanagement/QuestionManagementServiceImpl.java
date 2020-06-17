package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;

public class QuestionManagementServiceImpl implements QuestionManagementService {

	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion basicQuestion) throws UserDefinedSQLException {

		boolean isQuestionCreated =false;
		QuestionManagementDao questionManagemetDao = SystemConfig.instance().getQuestionManagementDao();
		
		boolean isQuestionExists = questionManagemetDao.isQuestionExistForUserWithTitleandText(basicQuestion);
		
		if (!isQuestionExists) {
			isQuestionCreated = questionManagemetDao.createNumericOrTextQuestion(basicQuestion);
		}
		
		return isQuestionCreated;
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoice) throws UserDefinedSQLException {
		
		boolean isQuestionCreated =false;
		QuestionManagementDao questionManagemetDao = SystemConfig.instance().getQuestionManagementDao();
		
		boolean isQuestionExists = questionManagemetDao.isQuestionExistForUserWithTitleandText(multipleChoice);
		
		if (!isQuestionExists) {
			isQuestionCreated = questionManagemetDao.createMultipleChoiceQuestion(multipleChoice);
		}
		
		return isQuestionCreated;
	}

}
