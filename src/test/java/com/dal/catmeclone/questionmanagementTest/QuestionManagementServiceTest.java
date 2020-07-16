package com.dal.catmeclone.questionmanagementTest;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfigTest;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
import com.dal.catmeclone.model.*;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementDao;
import com.dal.catmeclone.questionmanagement.QuestionManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionManagementServiceTest {

    AbstractFactory abstractFactoryTest = SystemConfigTest.instance().getAbstractFactoryTest();
    ModelAbstractFactory modelFactory = abstractFactoryTest.createModelAbstractFactory();
    QuestionManagementAbstractFactory questionManagementAbstractFactory = abstractFactoryTest.createQuestionManagerAbstractFactory();
    QuestionManagementDao questionManagementDaomock;
    QuestionManagementService questionManagementService;

    @BeforeEach
    public void setup() {
        questionManagementDaomock = questionManagementAbstractFactory.createQuestionManagementDao();
        questionManagementService = questionManagementAbstractFactory.createQuestionManagementService(questionManagementDaomock);
    }

    @Test
    public void getAllQuestionsForUserWithQuestionsTest() throws UserDefinedException {

        User user = modelFactory.createUser("B00123456");
        assertTrue(questionManagementService.getAllQuestionByUser(user).size() == 2);
    }

    @Test
    public void getAllQuestionsForUserWithNoQuestionsTest() throws UserDefinedException {

        User user = modelFactory.createUser("B00987654");
        assertEquals(questionManagementService.getAllQuestionByUser(user).size(), 0);
    }

    @Test
    public void getSortedQuestionsByTitleTest() throws UserDefinedException {

        User user = modelFactory.createUser("B00123456");
        user.setBannerId("B00123456");
        assertTrue(questionManagementService.getAllQuestionByUser(user).get(0).getQuestionTitle().equals("Basic_Title_1"));
    }

    @Test
    public void getSortedQuestionsByDateTest() throws UserDefinedException {
        User user = modelFactory.createUser();
        user.setBannerId("B00123456");
        assertTrue(questionManagementService.getAllQuestionByUser(user).get(1).getQuestionTitle().equals("XYZ_Title_2"));
    }

    @Test
    public void createNumericOrTextQuestion() throws UserDefinedException {

        User user = modelFactory.createUser("B00123456");
        BasicQuestion numericQuestion = modelFactory.createBasicQuestion("Title_3", "Text_3", QuestionType.NUMERIC, new Date(), user);
        assertTrue(questionManagementService.createNumericOrTextQuestion(numericQuestion));
    }

    @Test
    public void createMultipleChoiceQuestion() throws UserDefinedException {

        User user = modelFactory.createUser("B00123456");
        MultipleChoiceQuestion multipleChoiceQuestion = modelFactory.createMultipleChoiceQuestion();
        multipleChoiceQuestion.setQuestionTitle("TITLE_4");
        multipleChoiceQuestion.setQuestionText("TEXT_4");
        multipleChoiceQuestion.setQuestionType(QuestionType.MULTIPLECHOICEMANY);
        multipleChoiceQuestion.setCreationDate(new Date());
        multipleChoiceQuestion.setCreatedByInstructor(user);
        List<Option> optionList = new ArrayList<Option>();
        optionList.add(modelFactory.createOption("FirstOption", 1));
        optionList.add(modelFactory.createOption("SecondOption", 2));
        optionList.add(modelFactory.createOption("", 3));
        multipleChoiceQuestion.setOptionList(optionList);

        assertTrue(questionManagementService.createMultipleChoiceQuestion(multipleChoiceQuestion));
    }

    @Test
    public void ifQuestionTitleandTextExistsTrueTest() throws UserDefinedException {

        User user = modelFactory.createUser("B00123456");
        BasicQuestion question = modelFactory.createBasicQuestion("Basic_Title_1", "Basic_Text_1", QuestionType.NUMERIC, new Date(), user);
        question.setQuestionId(1);

        assertTrue(questionManagementService.ifQuestionTitleandTextExists(question));
    }

    @Test
    public void ifQuestionTitleExistsButTextDiffersTest() throws UserDefinedException {
        User user = modelFactory.createUser("B00123456");
        BasicQuestion question = modelFactory.createBasicQuestion("Basic_Title_1", "Basic_Text_NO", QuestionType.NUMERIC, new Date(), user);
        question.setQuestionId(1);

        assertFalse(questionManagementService.ifQuestionTitleandTextExists(question));
    }

    @Test
    public void ifQuestionTitleTextExistsForDifferentUserTest() throws UserDefinedException {
        User user = modelFactory.createUser("B00000000");
        BasicQuestion question = modelFactory.createBasicQuestion("Basic_Title_1", "Basic_Text_1", QuestionType.NUMERIC, new Date(), user);
        question.setQuestionId(1);

        assertFalse(questionManagementService.ifQuestionTitleandTextExists(question));
    }

    @Test
    public void ifQuestionTextExistsButTitleDiffersTest() throws UserDefinedException {
        User user = modelFactory.createUser("B00123456");
        BasicQuestion question = modelFactory.createBasicQuestion("Basic_Title_1", "Basic_Text_NO", QuestionType.NUMERIC, new Date(), user);
        question.setQuestionId(1);

        assertFalse(questionManagementService.ifQuestionTitleandTextExists(question));
    }

    public void deleteQuestionforIdExist() throws UserDefinedException {

        assertFalse(questionManagementService.deleteQuestion(1));
    }

    public void deleteQuestionforIdNotExisting() throws UserDefinedException {

        assertFalse(questionManagementService.deleteQuestion(100));
    }


}
