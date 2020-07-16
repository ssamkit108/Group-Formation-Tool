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

    public BasicQuestion(String questionTitle, String questionText, QuestionType questionType, Date creationDate,
                         User createdByInstructor) {
        super();
        this.questionTitle = questionTitle;
        this.questionText = questionText;
        this.questionType = questionType;
        this.creationDate = creationDate;
        this.createdByInstructor = createdByInstructor;
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

    public BasicQuestion(int questionId, QuestionType questionType) {
        super();
        this.questionId = questionId;
        this.questionType = questionType;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + questionId;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicQuestion other = (BasicQuestion) obj;
        if (questionId != other.questionId) {
            return false;
        }
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
