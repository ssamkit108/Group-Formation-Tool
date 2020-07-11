package com.dal.catmeclone.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class MultipleChoiceQuestion extends BasicQuestion {

	private List<Option> optionList = new ArrayList<Option>();

	private Logger LOGGER = Logger.getLogger(MultipleChoiceQuestion.class.getName());

	public MultipleChoiceQuestion() {
		
		super();
		this.optionList.add(new Option(1));
		this.optionList.add(new Option(2));
		this.optionList.add(new Option(3));
		this.optionList.add(new Option(4));
		this.optionList.add(new Option(5));
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
			if (option.getOptionText().trim().isEmpty() || option.getOptionValue() == -1) {
				LOGGER.info("Removing the empty option ( Text  or value ) from the list" + option.toString());
				it.remove();
			}
		}
	}
	
	public boolean areAllOptionValid()
	{
		boolean optionvalid= false;
		Iterator<Option> it = optionList.iterator();
		while (it.hasNext()) {
			Option option = it.next();
			if (!option.getOptionText().trim().isEmpty() && option.getOptionValue()!=-1) {
				optionvalid = true;
				break;
			}
		}
		return optionvalid;
	}

}
