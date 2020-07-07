package com.dal.catmeclone.surveyresponse;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactory;
import com.dal.catmeclone.DBUtility.DBUtilityAbstractFactoryImpl;
import com.dal.catmeclone.DBUtility.DataBaseConnection;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.questionmanagement.QuestionManagementDaoImpl;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.parameters.P;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class ResponseDaoImpl implements ResponseDao {

    Logger LOGGER = Logger.getLogger(ResponseDaoImpl.class.getName());

    AbstractFactory abstractFactory= SystemConfig.instance().getAbstractFactory();
    DBUtilityAbstractFactory dbUtilityAbstractFactory=abstractFactory.createDBUtilityAbstractFactory();
    SurveyResponseAbstractFactory surveyResponseAbstractFactory=abstractFactory.createSurveyResponseAbstractFactory();
    ModelAbstractFactory modelAbstractFactory=abstractFactory.createModelAbstractFactory();
    Properties properties = SystemConfig.instance().getProperties();

    private DataBaseConnection DBUtil;
    private Connection connection;
    ResultSet rs;
    ResultSet rs_options;

    public List<SurveyQuestionResponse> getAllQuestion(int courseid) throws Exception {
        DBUtil = dbUtilityAbstractFactory.createDataBaseConnection();
        List<SurveyQuestionResponse> listOfQuestions=new ArrayList<SurveyQuestionResponse>();
        CallableStatement statement = null;
        CallableStatement statement_options = null;
        Map<Integer,Integer> survey_question=new TreeMap<Integer, Integer>();
        try {
            connection = DBUtil.connect();
            LOGGER.info("Retrieving from database");
            statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_surveryquestions") + "}");
            statement.setInt(1,courseid);
            LOGGER.info("Querying Database to fetch list of question for the survey");
            rs = statement.executeQuery();

            while(rs.next()){
                survey_question.put(rs.getInt(1),rs.getInt(2));
            }


            for(Map.Entry<Integer,Integer> entry:survey_question.entrySet()) {
                SurveyQuestionResponse surveyQuestionResponse=modelAbstractFactory.createSurveyQuestionResponse();
                SurveyQuestion surveyQuestion=modelAbstractFactory.createSurveyQuestion();

                surveyQuestion.setSurveyQuestionId(entry.getKey());
                statement = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_basicquestion") + "}");
                statement.setInt(1, entry.getValue());
                rs = statement.executeQuery();

                while (rs.next()) {
                    BasicQuestion basicQuestion=modelAbstractFactory.createBasicQuestion();
                    basicQuestion.setQuestionId(rs.getInt(1));
                    basicQuestion.setQuestionTitle(rs.getString(2));
                    basicQuestion.setQuestionText(rs.getString(3));
                    basicQuestion.setQuestionType(QuestionType.valueOf(rs.getString(4)));

                    if(basicQuestion.getQuestionType() == QuestionType.NUMERIC
                            || basicQuestion.getQuestionType() == QuestionType.FREETEXT){
                        surveyQuestion.setQuestionDetail(basicQuestion);
                    }
                    else{
                        List<Option> optionList=new ArrayList<Option>();
                        MultipleChoiceQuestion multipleChoiceQuestion=modelAbstractFactory.createMultipleChoiceQuestion();
                        multipleChoiceQuestion.setQuestionId(basicQuestion.getQuestionId());
                        multipleChoiceQuestion.setQuestionTitle(basicQuestion.getQuestionTitle());
                        multipleChoiceQuestion.setQuestionText(basicQuestion.getQuestionText());
                        multipleChoiceQuestion.setQuestionType(basicQuestion.getQuestionType());

                        statement_options = connection.prepareCall("{call " + properties.getProperty("procedure.fetch_multiplechoice") + "}");
                        statement_options.setInt(1, entry.getValue());
                        rs_options = statement_options.executeQuery();

                        while (rs_options.next()){
                            optionList.add(new Option(rs_options.getString("option_text"),rs_options.getInt("option_value")));
                        }

                        multipleChoiceQuestion.setOptionList(optionList);
                        surveyQuestion.setQuestionDetail(multipleChoiceQuestion);
                    }
                }
                surveyQuestionResponse.setSurveyQuestion(surveyQuestion);
                listOfQuestions.add(surveyQuestionResponse);
            }
            return listOfQuestions;
        } catch (UserDefinedSQLException e) {
            LOGGER.warning(e.getLocalizedMessage());
            throw new UserDefinedSQLException("SQL exception generated in ResponseDaoImpl "+e.getLocalizedMessage());
        }catch (Exception e){
            LOGGER.warning(e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        }finally {
            // Closing the Statement and Connection
            if (null != statement) {
                DBUtil.terminateStatement(statement);
            }
            if(null != statement_options){
                DBUtil.terminateStatement(statement_options);
            }
            DBUtil.terminateConnection();
        }

    }
}
