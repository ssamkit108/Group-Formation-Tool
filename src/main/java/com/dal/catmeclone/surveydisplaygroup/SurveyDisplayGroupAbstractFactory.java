package com.dal.catmeclone.surveydisplaygroup;

public interface SurveyDisplayGroupAbstractFactory {

    SurveyDisplayGroupService createSurveyDisplayGroupService();

    SurveyDisplayGroupService createSurveyDisplayGroupService(SurveyDisplayGroupDao surveyDisplayGroupDao);

    SurveyDisplayGroupDao createSurveyDisplayGroupDao();

}
