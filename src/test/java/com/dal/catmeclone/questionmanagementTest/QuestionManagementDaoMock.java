package com.dal.catmeclone.questionmanagementTest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementDaoMock implements QuestionManagementDao {

	private ArrayList<BasicQuestion> basicQuestionDetailsList;
	private ArrayList<MultipleChoiceQuestion> mulipleChoiceQuestionList;
	
	
	
	public QuestionManagementDaoMock()
	{
		this.basicQuestionDetailsList = new ArrayList<BasicQuestion>();
		this.mulipleChoiceQuestionList = new ArrayList<MultipleChoiceQuestion>();
		BasicQuestion basicQuestion1= new BasicQuestion(1,"Basic_Title_1", "Basic_Text_1", QuestionType.NUMERIC,new Date(), new User("B00123456"));
		BasicQuestion basicQuestion2= new BasicQuestion(2,"XYZ_Title_2", "XYZ_Text_2", QuestionType.FREETEXT,new Date(), new User("B00123456"));
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
