/**
 * 
 */
package com.dal.catmeclone.questionmanagementTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;

/**
 * @author Mayank
 *
 */
public class QuestionModelMock extends MultipleChoiceQuestion {
	
	private List<Option> optionList = new ArrayList<Option>();
	
	/**
	 * 
	 */
	public QuestionModelMock(List<Option> optionList) {
		super();
		this.optionList = optionList;
		
	}
	
	
	public void filterOptions() {
		Iterator<Option> it = this.optionList.iterator();
		while (it.hasNext()) {
			Option option = it.next();
			if (option.getOptionText().trim().isEmpty() || option.getOptionValue() == -1) {
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


	public List<Option> getOptionList() {
		return optionList;
	}


	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	

}
