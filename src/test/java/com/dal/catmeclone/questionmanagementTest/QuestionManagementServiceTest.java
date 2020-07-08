package com.dal.catmeclone.questionmanagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.junit.jupiter.api.Test;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.AbstractFactoryTest;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementServiceTest{
	
	AbstractFactoryTest abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();

	@Test
	public void getAllQuestionsForUserWithQuestionsTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		User user = new User();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).size()==2);
	}
	
	@Test
	public void getAllQuestionsForUserWithNoQuestionsTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		User user = new User();		
		user.setBannerId("B00987654");
		mock.getAllQuestionByUser(user);
		assertEquals(mock.getAllQuestionByUser(user).size(), 0);
	}
	
	@Test
	public void getSortedQuestionsByTitleTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		User user = new User();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).get(0).getQuestionTitle().equals("Basic_Title_1"));	
	}
	
	@Test
	public void getSortedQuestionsByDateTest() throws UserDefinedSQLException {
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		User user = new User();		
		user.setBannerId("B00123456");
		mock.getAllQuestionByUser(user);
		assertTrue(mock.getAllQuestionByUser(user).get(1).getQuestionTitle().equals("XYZ_Title_2"));
	}
	
	@Test
	public void createNumericOrTextQuestion() throws UserDefinedSQLException
	{
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		BasicQuestion numericQuestion = new BasicQuestion("Title_3", "Text_3", QuestionType.NUMERIC, new Date(), new User("B00123456"));
		assertTrue(mock.createNumericOrTextQuestion(numericQuestion));
	}

	@Test
	public void createMultipleChoiceQuestion() throws UserDefinedSQLException {
		
		QuestionManagementDao mock = abstractFactoryTest.createQuestionManagerAbstractFactory().createQuestionManagementDao();
		MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
		multipleChoiceQuestion.setQuestionTitle("TITLE_4");
		multipleChoiceQuestion.setQuestionText("TEXT_4");
		multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLECHOICEMANY);
		multipleChoiceQuestion.setCreationDate(new Date());
		multipleChoiceQuestion.setCreatedByInstructor(new User("B00123456"));
		assertTrue(mock.createMultipleChoiceQuestion(multipleChoiceQuestion));
	}

	@Test
	public void ifQuestionTitleandTextExistsTrueTest()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		BasicQuestion Question1= new BasicQuestion(1,"Basic_Title_1", "Basic_Text_1", QuestionType.NUMERIC,new Date(), new User("B00123456"));
		assertTrue(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTitleExistsButTextDiffersTest()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		BasicQuestion Question1= new BasicQuestion(1,"Basic_Title_1", "Basic_Text_NO", QuestionType.NUMERIC,new Date(), new User("B00123456"));
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTitleTextExistsForDifferentUserTest()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		BasicQuestion Question1= new BasicQuestion(1,"Basic_Title_1", "Basic_Text_1", QuestionType.NUMERIC,new Date(), new User("B00000000"));
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	@Test
	public void ifQuestionTextExistsButTitleDiffersTest()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		BasicQuestion Question1= new BasicQuestion(1,"Basic_Title_0", "Basic_Text_1", QuestionType.NUMERIC,new Date(), new User("B00123456"));
		assertFalse(mock.isQuestionExistForUserWithTitleandText(Question1));
	}
	
	public void deleteQuestionforIdExist()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		assertFalse(mock.deleteQuestion(1));
	}
	
	public void deleteQuestionforIdNotExisting()
	{
		QuestionManagementDaoMock mock = new QuestionManagementDaoMock();
		assertFalse(mock.deleteQuestion(100));
	}


	
	
}
