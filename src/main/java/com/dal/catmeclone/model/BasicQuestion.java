package com.dal.catmeclone.model;

import java.util.Date;

public class BasicQuestion {

	private int questionId;
	private String questionTitle;
	private String questionText;
	private QuestionType questionType;
	private Date creationDate;
	private User createdByInstructor;

	public BasicQuestion() {
		super();

	}

	public BasicQuestion(String questionTitle, Date date) {
		super();
		this.questionTitle = questionTitle;
		this.creationDate = date;
	}

	public BasicQuestion(String questionTitle, String questionText) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
	}

	public BasicQuestion(String questionTitle, String questionText, QuestionType questionType, Date date) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = questionType;
		this.creationDate = date;
	}

	public BasicQuestion(int questionId, String questionTitle, String questionText, QuestionType questionType,
			Date creationDate, User createdByInstructor) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = questionType;
		this.creationDate = creationDate;
		this.createdByInstructor = createdByInstructor;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreatedByInstructor() {
		return createdByInstructor;
	}

	public void setCreatedByInstructor(User createdByInstructor) {
		this.createdByInstructor = createdByInstructor;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}
