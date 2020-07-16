package com.dal.catmeclone.questionmanagement;

public class QuestionManagementAbstractFactoryImpl implements QuestionManagementAbstractFactory {

    @Override
    public QuestionManagementDao createQuestionManagementDao() {
        return new QuestionManagementDaoImpl();
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