package com.dal.catmeclone.questionmanagementTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dal.catmeclone.IAbstractFactory;
import com.dal.catmeclone.SystemConfigT;
import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.modelTest.IModelAbstractFactory;

public class QuestionModelTest {
	
	private List<Option> optionList = new ArrayList<Option>();
	IAbstractFactory abstractFactoryTest = SystemConfigT.instance().getAbstractFactoryTest();
	IModelAbstractFactory modelfact = abstractFactoryTest.createModelAbstractFactory();
	IQuestionManagementAbstractFactory quesmgmt = abstractFactoryTest.createQuestionManagerAbstractFactory();
	QuestionModelMock questionMock;
	
	@BeforeEach
	public void setup()
	{
		Option opt1 = modelfact.createOption();
		opt1.setOptionText("");
		opt1.setOptionValue(1);
		Option opt2 = modelfact.createOption();
		opt2.setOptionText("text1");
		opt2.setOptionValue(2);
		Option opt3 = modelfact.createOption();
		opt3.setOptionText("text2");
		opt3.setOptionValue(3);
		Option opt4 = modelfact.createOption();
		opt4.setOptionText("");
		opt4.setOptionValue(4);
		Option opt5 = modelfact.createOption();
		opt5.setOptionText("text4");
		opt5.setOptionValue(5);
		optionList.add(opt1);
		optionList.add(opt2);
		optionList.add(opt3);
		optionList.add(opt4);
		optionList.add(opt5);
		questionMock = quesmgmt.createQuestionModelMock(optionList);
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
		Option opt1 = modelfact.createOption();
		opt1.setOptionText("");
		opt1.setOptionValue(-1);
		Option opt2 = modelfact.createOption();
		opt2.setOptionText("");
		opt2.setOptionValue(-1);
		Option opt3 = modelfact.createOption();
		opt3.setOptionText("");
		opt3.setOptionValue(-1);
		optionList.add(opt1);
		optionList.add(opt2);
		optionList.add(opt3);
		assertTrue(questionMock.areAllOptionValid());
	}
}
