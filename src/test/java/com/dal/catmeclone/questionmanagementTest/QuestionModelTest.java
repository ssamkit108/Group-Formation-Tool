package com.dal.catmeclone.questionmanagementTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class QuestionModelTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    MultipleChoiceQuestion questionMock = modelFactory.createMultipleChoiceQuestion();

    @BeforeEach
    public void setup() {
    	List<Option> optionList = new ArrayList<Option>();
        Option option1 = modelFactory.createOption("",1);
        Option option2 = modelFactory.createOption("text1",2); 
        Option option3 = modelFactory.createOption("text2",3);
        Option option4 = modelFactory.createOption("text3",-1);
        Option option5 = modelFactory.createOption("text4",5);
        optionList.add(option1);
        optionList.add(option2);
        optionList.add(option3);
        optionList.add(option4);
        optionList.add(option5);
        questionMock.setOptionList(optionList);
    }
    
    @Test
    public void allOptionValidFalse() {
    	questionMock.getOptionList().add(modelFactory.createOption("",-1));
        assertTrue(questionMock.areAllOptionValid());
    }

    @Test
    public void filterOptions() {
        questionMock.filterOptions();
        assertTrue(questionMock.getOptionList().size() == 3);
    }

    @Test
    public void allOptionValidTrue() {
    	questionMock.filterOptions();
        assertTrue(questionMock.areAllOptionValid());
    }

}
