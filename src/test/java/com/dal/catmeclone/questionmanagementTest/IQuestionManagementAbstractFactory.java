package com.dal.catmeclone.questionmanagementTest;

import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

import java.util.List;

public interface IQuestionManagementAbstractFactory {
    public QuestionManagementDao createQuestionManagementDao();

    public QuestionModelMock createQuestionModelMock(List<Option> optionList);

}
