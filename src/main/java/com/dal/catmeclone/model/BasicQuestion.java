package com.dal.catmeclone.model;

public class BasicQuestion {
	
	private String questionTitle;
	private String questionText;
	private QuestionType questionType;

	
	
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
	

}
