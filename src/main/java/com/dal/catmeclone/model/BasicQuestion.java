package com.dal.catmeclone.model;

import java.util.Date;

public class BasicQuestion {
	
	private String questionTitle;
	private String questionText;
	private QuestionType questionType;
	private Date date;

	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BasicQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BasicQuestion(String questionTitle, String questionText, QuestionType questionType, Date date) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = questionType;
		this.date = date;
	}
	
	public BasicQuestion(String questionTitle, Date date) {
		super();
		this.questionTitle = questionTitle;
		this.date = date;
	}
	
	public BasicQuestion(String questionTitle, String questionText) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
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
	

}
