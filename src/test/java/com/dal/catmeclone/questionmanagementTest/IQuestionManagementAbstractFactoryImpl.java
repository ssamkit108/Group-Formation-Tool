package com.dal.catmeclone.questionmanagementTest;

import java.util.List;

import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class IQuestionManagementAbstractFactoryImpl implements IQuestionManagementAbstractFactory{

	@Override
	public QuestionManagementDao createQuestionManagementDao() {
		return new QuestionManagementDaoMock();
	}

	@Override
	public QuestionModelMock createQuestionModelMock(List<Option> optionList) {
		return new QuestionModelMock(optionList);
	}

}
