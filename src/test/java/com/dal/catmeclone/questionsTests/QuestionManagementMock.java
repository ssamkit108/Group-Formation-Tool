package com.dal.catmeclone.questionsTests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
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
	public boolean isQuestionExistForUserWithTitleandText(BasicQuestion basicQuestion) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createNumericOrTextQuestion(BasicQuestion textOrNumericQuestion) throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceChoose)
			throws UserDefinedSQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteQuestion(int questionId) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public boolean deleteQuestion(int questionId) throws SQLException, UserDefinedSQLException {
		// TODO Auto-generated method stub
		return false;
	}*/

	

}
