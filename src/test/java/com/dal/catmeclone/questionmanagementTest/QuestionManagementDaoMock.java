package com.dal.catmeclone.questionmanagementTest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementDaoMock implements QuestionManagementDao {

	private ArrayList<BasicQuestion> basicQuestionDetailsList;
	private ArrayList<MultipleChoiceQuestion> mulipleChoiceQuestionList;
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();    

	
	
	public QuestionManagementDaoMock()
	{
		this.basicQuestionDetailsList = new ArrayList<BasicQuestion>();
		this.mulipleChoiceQuestionList = new ArrayList<MultipleChoiceQuestion>();
		BasicQuestion basicQuestion1 = modelfact.createBasicQuestion();
		basicQuestion1.setQuestionId(1);
		basicQuestion1.setQuestionTitle("Basic_Title_1");
		basicQuestion1.setQuestionText("Basic_Text_1");
		basicQuestion1.setQuestionType(QuestionType.NUMERIC);
		basicQuestion1.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00123456");
		basicQuestion1.setCreatedByInstructor(usr);
		BasicQuestion basicQuestion2 = modelfact.createBasicQuestion();
		basicQuestion2.setQuestionId(2);
		basicQuestion2.setQuestionTitle("XYZ_Title_2");
		basicQuestion2.setQuestionText("XYZ_Text_2");
		basicQuestion2.setQuestionType(QuestionType.FREETEXT);
		basicQuestion2.setCreationDate(new Date());
		User usr2 = modelfact.createUser();
		usr2.setBannerId("B00123456");
		basicQuestion2.setCreatedByInstructor(usr2);
		this.basicQuestionDetailsList.add(basicQuestion1);
		this.basicQuestionDetailsList.add(basicQuestion2);
		
	}

	@Override
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) {
	
		for(BasicQuestion question: this.basicQuestionDetailsList)
		{
			if(question.getQuestionText().equalsIgnoreCase(basicQuestion.getQuestionText()) 
					&& question.getQuestionTitle().equalsIgnoreCase(basicQuestion.getQuestionTitle()) 
						&&question.getCreatedByInstructor().getBannerId().equals(basicQuestion.getCreatedByInstructor().getBannerId()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion){
		// Adding the question to numeric and text choice question list
		basicQuestionDetailsList.add(textOrNumericQuestion);
		return true;
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceChoose) {
		// Adding the question to multiple choice question list
		mulipleChoiceQuestionList.add(multipleChoiceChoose);
		return true;
	}



	@Override
	public List<BasicQuestion> getAllQuestionByUser(User user){
		
		//Returning the basic question DetailsList
		ArrayList<BasicQuestion> questionListForUser = new ArrayList<BasicQuestion>();
		for(BasicQuestion question: basicQuestionDetailsList)
		{
			if(question.getCreatedByInstructor().getBannerId().equalsIgnoreCase(user.getBannerId()))
			{
				questionListForUser.add(question);
			}
		}
		return questionListForUser;
		
	}

	@Override
	public boolean deleteQuestion(int questionId) {
		
		//since question id will be auto increment in database so it is basically acts as index for array list, with index starting from 1
		for(BasicQuestion question : basicQuestionDetailsList)
		{
			if(question.getQuestionId()==questionId)
			{
				//Id Found, Id will be deleted and returned true
				return true;
			}
		}
		
		//Id Not Found, returned false
		return false;
		
		
	}


	

}
