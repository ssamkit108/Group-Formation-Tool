package com.dal.catmeclone.model;

import java.util.Date;

public class BasicQuestion {

	private int questionId;
	private String questionTitle;
	private String questionText;
	private QuestionType questionType;
	private Date creationDate;
	private User createdByInstructor;

	/**
	 * 
	 */
	public BasicQuestion() {
		super();
	}

	

	/**
	 * @param questionId
	 * @param questionTitle
	 * @param questionText
	 * @param questionType
	 */
	public BasicQuestion(int questionId, String questionTitle, String questionText, String questionType) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = Enum.valueOf(QuestionType.class, questionType);
	}



	public BasicQuestion(String questionTitle, String questionText) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
	}

	/**
	 * @param questionTitle
	 * @param questionText
	 * @param questionType
	 * @param creationDate
	 * @param createdByInstructor
	 */
	public BasicQuestion(String questionTitle, String questionText, QuestionType questionType, Date creationDate, 
			User createdByInstructor) {
		super();
		this.questionTitle = questionTitle;
		this.questionText = questionText;
		this.questionType = questionType;
		this.creationDate = creationDate;
		this.createdByInstructor = createdByInstructor;
	}


	/**
	 * @param questionId
	 * @param questionTitle
	 * @param questionText
	 * @param questionType
	 * @param creationDate
	 * @param createdByInstructor
	 */
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

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	/**
	 * @return the questionTitle
	 */
	public String getQuestionTitle() {
		return questionTitle;
	}

	/**
	 * @param questionTitle the questionTitle to set
	 */
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	/**
	 * @return the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * @param questionText the questionText to set
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	/**
	 * @return the questionType
	 */
	public QuestionType getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the createdByInstructor
	 */
	public User getCreatedByInstructor() {
		return createdByInstructor;
	}

	/**
	 * @param createdByInstructor the createdByInstructor to set
	 */
	public void setCreatedByInstructor(User createdByInstructor) {
		this.createdByInstructor = createdByInstructor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + questionId;
		return result;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicQuestion other = (BasicQuestion) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}



	public boolean isQuestionTitleValid() {
		if (null != this.questionTitle && !this.questionTitle.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}



	

	
	

}
