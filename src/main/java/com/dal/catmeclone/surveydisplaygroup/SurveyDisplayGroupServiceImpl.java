package com.dal.catmeclone.surveydisplaygroup;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class SurveyDisplayGroupServiceImpl implements SurveyDisplayGroupService {

    private Logger LOGGER = Logger.getLogger(SurveyDisplayGroupServiceImpl.class.getName());

    private AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    private SurveyDisplayGroupAbstractFactory surveyDisplayGroupAbstractFactory = abstractFactory.createSurveyDisplayGroupAbstractFactory();
    private SurveyDisplayGroupDao surveyDisplayGroupDao = surveyDisplayGroupAbstractFactory.createSurveyDisplayGroupDao();

    public SurveyDisplayGroupServiceImpl() {
        super();
    }

    public SurveyDisplayGroupServiceImpl(SurveyDisplayGroupDao surveyDisplayGroupDao) {
        super();
        this.surveyDisplayGroupDao = surveyDisplayGroupDao;
    }

    @Override
    public HashMap<String, List<User>> retrievegroupinfo(int courseid) throws UserDefinedException {
        LOGGER.info("Retrieving Details for all the groups in database");
        HashMap<String, List<User>> groupData = surveyDisplayGroupDao.retrievegroupinfo(courseid);
        return groupData;
    }

    @Override
    public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedException {
        LOGGER.info("Retrieving Response Details for all a particular user");
        HashMap<String, List<Object>> response = surveyDisplayGroupDao.fetchresponse(courseid, bannerid);
        return response;
    }

}
