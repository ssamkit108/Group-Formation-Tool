package com.dal.catmeclone.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.dal.catmeclone.questionmanagement.QuestionManagementController;

public class MultipleChoiceQuestion extends BasicQuestion{

	private List<Option> optionList=new ArrayList<Option>();
	
	private Logger LOGGER = Logger.getLogger(MultipleChoiceQuestion.class.getName());

	public MultipleChoiceQuestion() {
		super();
		optionList.add(new Option(1));
		optionList.add(new Option(2));
		optionList.add(new Option(3));
		optionList.add(new Option(4));
		optionList.add(new Option(5));
		
		// TODO Auto-generated constructor stub
	}
	
	

	public List<Option> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}



	@Override
	public String toString() {
		return "MultipleChoiceQuestion [optionList=" + optionList + ", getQuestionTitle()=" + getQuestionTitle()
				+ ", getQuestionText()=" + getQuestionText() + ", getQuestionType()=" + getQuestionType() + "]";
	}
	
	
	public void filterOptions() {
		Iterator<Option> it = optionList.iterator();
		while (it.hasNext()) {
			Option option = it.next();
			if (option.getOptionText().trim().isEmpty()) {
				LOGGER.info("Removing the empty option from the list"+option.toString());
				it.remove();
			}
		}
	}
	
	

}
