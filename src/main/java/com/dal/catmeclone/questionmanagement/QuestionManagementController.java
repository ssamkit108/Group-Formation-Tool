package com.dal.catmeclone.questionmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;


@RequestMapping("/questionmanager")
@Controller
public class QuestionManagementController {

	private Logger LOGGER = Logger.getLogger(QuestionManagementController.class.getName());
	
	 private static final String AJAX_HEADER_NAME = "X-Requested-With";
	 private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";


	@GetMapping("")
	public String getCourseQuestionPage() {
		return "questionmanager/questionmanagerhome";
	}

	@GetMapping("/getCreateQuestionHome")
	public String getcreateQuestionPage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		return "QuestionCreateHome";
	}
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String getQuestionPage(Model model) {

		BasicQuestion basicQuestion = new BasicQuestion();
		model.addAttribute("BasicQuestion", basicQuestion);
		
		return "questionmanager/questioncreate";
		
	}

	
	@PostMapping("/question")
	public String getQuestionBasicDetails(@ModelAttribute("question") BasicQuestion basicQuestion, Model model) {

		LOGGER.info("Formulating questions based on question type");
		
		System.out.println(basicQuestion.getQuestionType());
		

		if (basicQuestion.getQuestionType()==QuestionType.NUMERIC
				|| basicQuestion.getQuestionType()==QuestionType.FREETEXT) {
			model.addAttribute("question", basicQuestion);
			return "questionmanager/numericortextquestion";
		}
		if (basicQuestion.getQuestionType()==QuestionType.MULTIPLECHOICEMANY
				|| basicQuestion.getQuestionType()==QuestionType.MULTIPLECHOICEONE) {
			MultipleChoiceQuestion multipleChoice = new MultipleChoiceQuestion();
			multipleChoice.setQuestionText(basicQuestion.getQuestionText());
			multipleChoice.setQuestionTitle(basicQuestion.getQuestionTitle());
			multipleChoice.setQuestionType(basicQuestion.getQuestionType());
			
			System.out.println(multipleChoice.getOptionList()+multipleChoice.getQuestionText());
			model.addAttribute("question", multipleChoice);
			return "questionmanager/multiplechoicequestion";
		}

		return "error";
	}
	
	@PostMapping(params = "addOption", path = {"question/multiplechoice", "/option/{id}"})
    public String addOrder(MultipleChoiceQuestion question, HttpServletRequest request,final BindingResult bindingResult) {
		 System.out.println("hete");
		 question.getOptionList().add(new Option(question.getOptionList().size()+1));
        
        	
        return "questionmanager/multiplechoicequestion";
      
    }

    // "removeItem" parameter contains index of a item that will be removed.
    @PostMapping(params = "removeItem", path = {"/option", "/option/{id}"})
    public String removeOrder(MultipleChoiceQuestion question, @RequestParam("removeItem") int index, HttpServletRequest request) {
        //order.items.remove(index);
        question.getOptionList().add(new Option(question.getOptionList().size()+1));
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "order::#items";
        } else {
            return "order";
        }
    }

	@RequestMapping(value = "question/numericorfree", method = RequestMethod.POST)
	public String createNumericQuestion(@ModelAttribute("question") BasicQuestion basicQuestion, Model model) {

		LOGGER.info("creating "+basicQuestion.getQuestionType()+" question");
		QuestionManagementService questionManagemetService = SystemConfig.instance().getQuestionManagementService();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		boolean isQuestionCreated;
		try {
			basicQuestion.setCreatedByInstructor(new User(username));
			basicQuestion.setCreationDate(new Date());
			isQuestionCreated = questionManagemetService.createNumericOrTextQuestion(basicQuestion);
			if(isQuestionCreated) {
				return "questionmanager/QuestionCreateSuccess";
			}
			else {
				return "/error";
			}
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			return "/error";
		}
	}

	
	@RequestMapping(value = "question/multiplechoice", method = RequestMethod.POST)
	public String createMultipleChoiceQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice, Model model) {

		LOGGER.info("creating "+multipleChoice.getQuestionType()+" question");
		QuestionManagementService questionManagemetService = SystemConfig.instance().getQuestionManagementService();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		boolean isQuestionCreated;
		try {
			multipleChoice.setCreatedByInstructor(new User(username));
			multipleChoice.setCreationDate(new Date());
			isQuestionCreated = questionManagemetService.createNumericOrTextQuestion(multipleChoice);
			if(isQuestionCreated) {
				return "questionmanager/QuestionCreateSuccess";
			}
			else {
				return "/error";
			}
		} catch (UserDefinedSQLException e) {
			// TODO Auto-generated catch block
			return "/error";
		}
	}

}
