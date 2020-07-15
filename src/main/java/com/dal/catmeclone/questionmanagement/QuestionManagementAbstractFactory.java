package com.dal.catmeclone.questionmanagement;

public interface QuestionManagementAbstractFactory {
    public QuestionManagementDao createQuestionManagementDao();

    public QuestionManagementService createQuestionManagementService();
}
