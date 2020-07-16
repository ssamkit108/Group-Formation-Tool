package com.dal.catmeclone.surveydisplaygroup;

import java.util.HashMap;
import java.util.List;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.User;

public interface SurveyDisplayGroupDao {

	public HashMap<String,List<User>> retrievegroupinfo(int courseid) throws UserDefinedException;
	public HashMap<String, List<Object>> fetchresponse(int courseid, String bannerid) throws UserDefinedException;
}
