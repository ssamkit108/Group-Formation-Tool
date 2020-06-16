package com.dal.catmeclone.questionmanagement;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.MultipleChoiceQuestion;
import com.dal.catmeclone.model.Option;
import com.dal.catmeclone.model.QuestionType;


@RequestMapping("/questionmanager")
@Controller
public class QuestionManagementController {

	private Logger log = Logger.getLogger(QuestionManagementController.class.getName());


	@GetMapping("")
	public String getCourseQuestionPage() {
		return "questionmanager/questionmanagerhome";
	}

	@GetMapping("/getCreateQuestionHome")
	public String getcreateQuestionPage(Model model) {
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

		log.info("confirming questions based on type");

		
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

	@RequestMapping(value = "question/numeric", method = RequestMethod.POST)
	public String createNumericQuestion(@ModelAttribute("question") BasicQuestion basicQuestion, Model model) {

		log.info("creating Numeric question");
		boolean isQuestionCreated =true;
		
		System.out.println(basicQuestion);
		//boolean isQuestionCreated=theCreateQuestionService.createNumericQuestion(basicQuestionData);
		if(isQuestionCreated) {
			return "questionmanager/QuestionCreateSuccess";
		}
		else {
			return "/error";
		}
	}

	@RequestMapping(value = "question/freetext", method = RequestMethod.POST)
	public String createFreeTextQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice, Model model) {

		log.info("creating Numeric question");
		boolean isQuestionCreated =true;
		
		System.out.println(multipleChoice);
		//boolean isQuestionCreated=theCreateQuestionService.createNumericQuestion(basicQuestionData);
		if(isQuestionCreated) {
			return "questionmanager/QuestionCreateSuccess";
		}
		else {
			return "/error";
		}
	}
	
	@RequestMapping(value = "question/multiplechoice", method = RequestMethod.POST)
	public String createMultipleChoiceQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice, Model model) {

		boolean isQuestionCreated =true;
		System.out.println(multipleChoice.toString());
		if(isQuestionCreated) {
			return "questionmanager/questioncreatesuccess";
		}
		else {
			return "/error";
		}
	}

}
