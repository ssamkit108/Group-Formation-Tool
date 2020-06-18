package com.dal.catmeclone.model;

public class Option {

	private String optionText;
	private int optionValue;

	public Option() {
		super();
	}

	public Option(int optionValue) {
		super();
		this.optionValue = optionValue;
	}

	public Option(String optionText, int optionValue) {
		super();
		this.optionText = optionText;
		this.optionValue = optionValue;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public int getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(int optionValue) {
		this.optionValue = optionValue;
	}

	@Override
	public String toString() {
		return "Option [optionText=" + optionText + ", optionValue=" + optionValue + "]";
	}

}
