package com.dal.catmeclone.questionmanagementTest;

import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;
import com.dal.catmeclone.questionmanagement.QuestionManagementService;
import com.dal.catmeclone.questionmanagement.QuestionManagementServiceImpl;


public class QuestionManagementAbstractFactoryTestImpl implements QuestionManagementAbstractFactory {

    @Override
    public QuestionManagementDao createQuestionManagementDao() {
        return new QuestionManagementDaoMock();
    }

	@Override
	public QuestionManagementService createQuestionManagementService() {
		return new QuestionManagementServiceImpl();
	}

	@Override
	public QuestionManagementService createQuestionManagementService(QuestionManagementDao questionManagementDao) {
		return new QuestionManagementServiceImpl(questionManagementDao);
	}

}
