package com.dal.catmeclone.questionmanagementTest;

import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementAbstractFactoryImpl implements QuestionManagementAbstractFactoryTest{

	@Override
	public QuestionManagementDao createQuestionManagementDao() {
		// TODO Auto-generated method stub
		return new QuestionManagementDaoMock();
	}

}
