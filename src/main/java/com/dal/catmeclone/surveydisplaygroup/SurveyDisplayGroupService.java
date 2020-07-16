package com.dal.catmeclone.surveydisplaygroup;

import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

import java.util.HashMap;
import java.util.List;

public interface SurveyDisplayGroupService {

    public HashMap<String, List<User>> retrievegroupinfo(int courseid) throws UserDefinedException;

    public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedException;

}
