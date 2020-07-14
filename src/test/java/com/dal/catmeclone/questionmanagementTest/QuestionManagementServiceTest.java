package com.dal.catmeclone.questionmanagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementServiceTest{
	
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
	IQuestionManagementAbstractFactory quesfact = abstractFactoryTest.createQuestionManagerAbstractFactory();

	@Test
	public void getAllQuestionsForUserWithQuestionsTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		User user = modelfact.createUser();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).size()==2);
	}
	
	@Test
	public void getAllQuestionsForUserWithNoQuestionsTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		User user = modelfact.createUser();		
		user.setBannerId("B00987654");
		mock.getAllQuestionByUser(user);
		assertEquals(mock.getAllQuestionByUser(user).size(), 0);
	}
	
	@Test
	public void getSortedQuestionsByTitleTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		User user = modelfact.createUser();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).get(0).getQuestionTitle().equals("Basic_Title_1"));	
	}
	
	@Test
	public void getSortedQuestionsByDateTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		User user = modelfact.createUser();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).get(1).getQuestionTitle().equals("XYZ_Title_2"));
	}
	
	@Test
	public void createNumericOrTextQuestion() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		BasicQuestion numericQuestion = modelfact.createBasicQuestion();
		numericQuestion.setQuestionTitle("Title_3");
		numericQuestion.setQuestionText("Text_3");
		numericQuestion.setQuestionType(QuestionType.NUMERIC);
		numericQuestion.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00123456");
		numericQuestion.setCreatedByInstructor(usr);
		assertTrue(mock.createNumericOrTextQuestion(numericQuestion));
	}

	@Test
	public void createMultipleChoiceQuestion() throws UserDefinedSQLException {
		
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		MultipleChoiceQuestion multipleChoiceQuestion = modelfact.createMultipleChoiceQuestion();
		multipleChoiceQuestion.setQuestionTitle("TITLE_4");
		multipleChoiceQuestion.setQuestionText("TEXT_4");
		multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLECHOICEMANY);
		multipleChoiceQuestion.setCreationDate(new Date());
		multipleChoiceQuestion.setCreatedByInstructor(new User("B00123456"));
		assertTrue(mock.createMultipleChoiceQuestion(multipleChoiceQuestion));
	}

	@Test
	public void ifQuestionTitleandTextExistsTrueTest() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		BasicQuestion Question1 = modelfact.createBasicQuestion();
		Question1.setQuestionId(1);
		Question1.setQuestionTitle("Basic_Title_1");
		Question1.setQuestionText("Basic_Text_1");
		Question1.setQuestionType(QuestionType.NUMERIC);
		Question1.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00123456");
		Question1.setCreatedByInstructor(usr);
		assertTrue(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTitleExistsButTextDiffersTest() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		BasicQuestion Question1 = modelfact.createBasicQuestion();
		Question1.setQuestionId(1);
		Question1.setQuestionTitle("Basic_Title_1");
		Question1.setQuestionText("Basic_Text_NO");
		Question1.setQuestionType(QuestionType.NUMERIC);
		Question1.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00123456");
		Question1.setCreatedByInstructor(usr);
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTitleTextExistsForDifferentUserTest() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		BasicQuestion Question1 = modelfact.createBasicQuestion();
		Question1.setQuestionId(1);
		Question1.setQuestionTitle("Basic_Title_1");
		Question1.setQuestionText("Basic_Text_1");
		Question1.setQuestionType(QuestionType.NUMERIC);
		Question1.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00000000");
		Question1.setCreatedByInstructor(usr);
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTextExistsButTitleDiffersTest() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		BasicQuestion Question1 = modelfact.createBasicQuestion();
		Question1.setQuestionId(1);
		Question1.setQuestionTitle("Basic_Title_1");
		Question1.setQuestionText("Basic_Text_NO");
		Question1.setQuestionType(QuestionType.NUMERIC);
		Question1.setCreationDate(new Date());
		User usr = modelfact.createUser();
		usr.setBannerId("B00123456");
		Question1.setCreatedByInstructor(usr);
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	public void deleteQuestionforIdExist() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		assertFalse(mock.deleteQuestion(1));
	}
	
	public void deleteQuestionforIdNotExisting() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = quesfact.createQuestionManagementDao();
		assertFalse(mock.deleteQuestion(100));
	}


	
	
}
