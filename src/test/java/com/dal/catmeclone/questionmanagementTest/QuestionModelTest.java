package com.dal.catmeclone.questionmanagementTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dal.catmeclone.model.Option;

public class QuestionModelTest {
	
	private List<Option> optionList = new ArrayList<Option>();
	QuestionModelMock questionMock;
	
	@BeforeEach
	public void setup()
	{
		optionList.add(new Option("",1));
		optionList.add(new Option("text1",2));
		optionList.add(new Option("text2",3));
		optionList.add(new Option("",4));
		optionList.add(new Option("text4",5));
		questionMock = new QuestionModelMock(optionList);
	}

	@Test
	public void filterOptions() {
		questionMock.filterOptions();
		assertTrue(questionMock.getOptionList().size()==3);
	}
	
	@Test
	public void allOptionValidTrue()
	{
		assertTrue(questionMock.areAllOptionValid());
	}
	
	@Test
	public void allOptionNotValid()
	{
		List<Option> optionList = new ArrayList<Option>();
		optionList.add(new Option("",-1));
		optionList.add(new Option("",-1));
		optionList.add(new Option("",-1));
		assertTrue(questionMock.areAllOptionValid());
	}
}
