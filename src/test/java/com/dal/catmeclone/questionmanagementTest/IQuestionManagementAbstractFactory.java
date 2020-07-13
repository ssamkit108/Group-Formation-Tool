package com.dal.catmeclone.questionmanagementTest;

import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public interface IQuestionManagementAbstractFactory {
	public QuestionManagementDao createQuestionManagementDao();
	public QuestionModelMock createQuestionModelMock(List<Option> optionList);

}
