package com.dal.catmeclone.model;

import java.util.Date;

public class BasicQuestion {
	
	private String questionTitle;
	private String questionText;
	private QuestionType questionType;
	private Date creationDate;
	private User createdByInstructor;
	
	
	public BasicQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public BasicQuestion(String questionTitle, String questionText, QuestionType questionType) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = questionType;
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
	
	
	
	
	

}
