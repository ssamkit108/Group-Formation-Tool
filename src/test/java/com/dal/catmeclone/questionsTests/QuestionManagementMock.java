package com.dal.catmeclone.questionsTests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;

public class QuestionManagementMock implements QuestionManagementDao {

	@Override
	public List<BasicQuestion> getAllQuestionByUser(User u) throws SQLException, UserDefinedSQLException {
		List<BasicQuestion> q = new ArrayList<BasicQuestion>();
		q.add(new BasicQuestion("Java","Rate?"));
		return q;
	}

	@Override
	public List<BasicQuestion> getSortedQuestionsByTitle(User u) throws SQLException, UserDefinedSQLException {
		List<BasicQuestion> q = new ArrayList<BasicQuestion>();
		q.add(new BasicQuestion("Java","Rate?"));
		return q;
	}

	@Override
	public List<BasicQuestion> getSortedQuestionsByDate(User u) throws SQLException, UserDefinedSQLException {
		List<BasicQuestion> q = new ArrayList<BasicQuestion>();
		q.add(new BasicQuestion("Java", "Rate?"));
		return q;
	}

	@Override
	public boolean deleteQuestionTitle(User u, BasicQuestion q) throws SQLException, UserDefinedSQLException {
		return true;
	}

}
