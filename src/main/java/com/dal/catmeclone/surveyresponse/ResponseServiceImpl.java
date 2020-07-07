package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.SurveyQuestionResponse;
import com.dal.catmeclone.questionmanagement.QuestionManagementDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ResponseServiceImpl implements ResponseService{

    Logger LOGGER = Logger.getLogger(ResponseServiceImpl.class.getName());

    ResponseDaoImpl responseDao=new ResponseDaoImpl();

    public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
        List<SurveyQuestionResponse> listofquestion=new ArrayList<SurveyQuestionResponse>();
        try{
            listofquestion=responseDao.getAllQuestion(courseid);
            return listofquestion;
        }catch (UserDefinedSQLException e){
            LOGGER.warning(e.getLocalizedMessage());
            throw new UserDefinedSQLException(e.getLocalizedMessage());
        }catch (Exception e){
            LOGGER.warning(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        }
    }

}
