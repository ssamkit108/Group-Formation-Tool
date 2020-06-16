package com.dal.catmeclone.model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion extends BasicQuestion{

	private List<Option> optionList=new ArrayList<Option>();
	
	

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
	
	

}
