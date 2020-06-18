package com.dal.catmeclone.questionsTests;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.User;

public class QuestionManagementTest {

	@Test
	public void getAllQuestionsTest() throws UserDefinedSQLException, SQLException {
		BasicQuestion q = new BasicQuestion();
		QuestionManagementMock mock = new QuestionManagementMock();
		q.setQuestionTitle("Java");
		q.setQuestionText("Rate?");
		User u = new User();
		u.setBannerId("B00854332");
		List<BasicQuestion> l = new ArrayList<BasicQuestion>();
		l.add(q);
		System.out.println(mock.getAllQuestionByUser(u).get(0));
		System.out.println(l.get(0));
		assertEquals(mock.getAllQuestionByUser(u).get(0).getQuestionTitle(), l.get(0).getQuestionTitle());	
	}
	
	@Test
	public void getSortedQuestionsByTitleTest() throws UserDefinedSQLException, SQLException {
		BasicQuestion q = new BasicQuestion();
		QuestionManagementMock mock = new QuestionManagementMock();
		q.setQuestionTitle("Java");
		q.setQuestionText("Rate?");
		User u = new User();
		u.setBannerId("B00854332");
		List<BasicQuestion> l = new ArrayList<BasicQuestion>();
		l.add(q);
		assertEquals(mock.getAllQuestionByUser(u).get(0).getQuestionTitle(), l.get(0).getQuestionTitle());	
	}
	
	@Test
	public void getSortedQuestionsByDateTest() throws UserDefinedSQLException, SQLException {
		BasicQuestion q = new BasicQuestion();
		QuestionManagementMock mock = new QuestionManagementMock();
		q.setQuestionTitle("Java");
		q.setQuestionText("Rate?");
		User u = new User();
		u.setBannerId("B00854332");
		List<BasicQuestion> l = new ArrayList<BasicQuestion>();
		l.add(q);
		assertEquals(mock.getAllQuestionByUser(u).get(0).getQuestionTitle(), l.get(0).getQuestionTitle());	
	}
	
	/*@SuppressWarnings("deprecation")
	@Test
	public void deleteQuestitonTitleTest() throws UserDefinedSQLException, SQLException {
		User u = new User();
		u.setBannerId("B00854332");
		QuestionManagementMock mock = new QuestionManagementMock();
		BasicQuestion q = new BasicQuestion();
		q.setQuestionTitle("Java");
		q.setQuestionText("Rate?");
		Assert.isTrue(mock.deleteQuestion(q.getQuestionId()));
	}*/
	
}
