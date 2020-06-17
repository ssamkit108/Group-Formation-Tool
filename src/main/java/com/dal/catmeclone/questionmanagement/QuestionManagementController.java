package com.dal.catmeclone.questionmanagement;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.dal.catmeclone.model.QuestionType;
import com.dal.catmeclone.model.User;


@RequestMapping("/questionmanager")
@Controller
public class QuestionManagementController {

	private Logger log = Logger.getLogger(QuestionManagementController.class.getName());
	
	QuestionManagementDao quest = SystemConfig.instance().getQuestionManagementDao();
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String viewQuestionTitlePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			model.addAttribute("questionList", quest.getAllQuestionByUser(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			model.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
		return "questionmanager/questionmanagerhome";
	}
	
	@RequestMapping(value= "", method=RequestMethod.POST, params="action=sortbytitle")
	public String viewQuestionSortedByTitle(@ModelAttribute BasicQuestion q, Model m)  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
	    try {
			m.addAttribute("questionList", quest.getSortedQuestionsByTitle(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
	    return "questionmanager/questionmanagerhome";
	}
	
	@RequestMapping(value= "", method=RequestMethod.POST, params="action=sortbydate")
	public String viewQuestionSortedByDate(@ModelAttribute BasicQuestion q, Model m)  {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
	    try {
			m.addAttribute("questionList", quest.getSortedQuestionsByDate(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			m.addAttribute("errormessage",e.getLocalizedMessage());
			return "error";
		}
	    return "questionmanager/questionmanagerhome";
	}
	
	@RequestMapping(value= "", method=RequestMethod.POST, params="action=remove")
	public String removeQuestion(@RequestParam(name = "questionTitle") String questionTitle, @RequestParam(name = "questionText") String questionText, Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		 try {
			 quest.deleteQuestionTitle(new User(username), new BasicQuestion(questionTitle, questionText));
			} catch (SQLException | UserDefinedSQLException e) {
				m.addAttribute("errormessage",e.getLocalizedMessage());
				return "error";
			}
		 return "redirect:questionmanager";
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
