package com.dal.catmeclone.questionmanagement;

import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

	private Logger logger = Logger.getLogger(QuestionManagementController.class.getName());

	QuestionManagementDao quest = SystemConfig.instance().getQuestionManagementDao();

	@GetMapping(value = "")
	public String viewQuestionTitlePage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			logger.info("Fetching the list of questions");
			model.addAttribute("questionList", quest.getAllQuestionByUser(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}

		return "questionmanager/questionmanagerhome";
	}

	@RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbytitle")
	public String viewQuestionSortedByTitleDB(@ModelAttribute BasicQuestion q, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			model.addAttribute("questionList", quest.getSortedQuestionsByTitle(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		return "questionmanager/questionmanagerhome";
	}

	@RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbydate")
	public String viewQuestionSortedByDate(@ModelAttribute BasicQuestion q, Model m) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			m.addAttribute("questionList", quest.getSortedQuestionsByDate(new User(username)));
		} catch (SQLException | UserDefinedSQLException e) {
			m.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		return "questionmanager/questionmanagerhome";
	}

	@PostMapping(value = "/delete")
	public String removeQuestion(@RequestParam(name = "question") int questionId, Model m) {
		try {
			quest.deleteQuestion(questionId);
		} catch (SQLException | UserDefinedSQLException e) {
			m.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		return "redirect:/questionmanager";
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

	@PostMapping("/questiondetails")
	public String getQuestionBasicDetails(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
			RedirectAttributes attributes) {

		LOGGER.info("Formulating questions based on question type");
		QuestionManagementService questionManagemetService = SystemConfig.instance().getQuestionManagementService();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		basicQuestion.setCreatedByInstructor(new User(username));
		System.out.println(basicQuestion.toString());

		try {
			boolean ifQuestionExist = questionManagemetService.ifQuestionTitleandTextExists(basicQuestion);

			if (!ifQuestionExist) {
				if (basicQuestion.getQuestionType() == QuestionType.NUMERIC
						|| basicQuestion.getQuestionType() == QuestionType.FREETEXT) {
					model.addAttribute("question", basicQuestion);
					return "questionmanager/numericortextquestion";
				}
				if (basicQuestion.getQuestionType() == QuestionType.MULTIPLECHOICEMANY
						|| basicQuestion.getQuestionType() == QuestionType.MULTIPLECHOICEONE) {
					MultipleChoiceQuestion multipleChoice = new MultipleChoiceQuestion();
					multipleChoice.setQuestionText(basicQuestion.getQuestionText());
					multipleChoice.setQuestionTitle(basicQuestion.getQuestionTitle());
					multipleChoice.setQuestionType(basicQuestion.getQuestionType());

					System.out.println(multipleChoice.getOptionList() + multipleChoice.getQuestionText());
					model.addAttribute("question", multipleChoice);
					return "questionmanager/multiplechoicequestion";
				}
			} else {
				attributes.addFlashAttribute("message", "Question Exists with same Question Title and Question Text");
				return "redirect:/questionmanager/question";
			}
		} catch (UserDefinedSQLException e) {

			model.addAttribute("errormessage", "Some Error occured. Please Try Again");
			return "/error";
		}

		return "error";
	}

	@PostMapping(params = "addOption", path = { "/question", "/order/{id}" })
	public String addOption(MultipleChoiceQuestion question, HttpServletRequest request,
			final BindingResult bindingResult, Model model) {
		System.out.println("hete");
		question.getOptionList().add(new Option(question.getOptionList().size() + 1));
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			// It is an Ajax request, render only #items fragment of the page.
			model.addAttribute("question", question);
			return "questionmanager/multiplechoicequestion::#details";
		} else {
			// It is a standard HTTP request, render whole page.
			return "questionmanager/multiplechoicequestion";
		}
	}

	// "removeItem" parameter contains index of a item that will be removed.
	@PostMapping(params = "removeItem", path = { "/option", "/option/{id}" })
	public String removeOrder(MultipleChoiceQuestion question, @RequestParam("removeItem") int index,
			HttpServletRequest request) {
		// order.items.remove(index);
		question.getOptionList().add(new Option(question.getOptionList().size() + 1));
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			return "order::#items";
		} else {
			return "order";
		}
	}

	@RequestMapping(value = "question/numericorfreetext", method = RequestMethod.POST)
	public String createNumericQuestion(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
			RedirectAttributes attributes) {

		LOGGER.info("creating " + basicQuestion.getQuestionType() + " question");
		QuestionManagementService questionManagemetService = SystemConfig.instance().getQuestionManagementService();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		boolean isQuestionCreated;
		try {
			basicQuestion.setCreatedByInstructor(new User(username));
			basicQuestion.setCreationDate(new Date());
			isQuestionCreated = questionManagemetService.createNumericOrTextQuestion(basicQuestion);
			if (isQuestionCreated) {
				attributes.addFlashAttribute("message", "Question Added Successfully");
				return "redirect:/questionmanager";
			} else {
				model.addAttribute("errormessage", "Some Error occured. Please Try Again");
				return "/error";
			}
		} catch (UserDefinedSQLException e) {
			model.addAttribute("errormessage", "Some Error occured. Please Try Again");
			return "/error";
		}
	}

	@RequestMapping(value = "question/multiplechoice", method = RequestMethod.POST)
	public String createMultipleChoiceQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice,
			Model model, RedirectAttributes attributes) {

		LOGGER.info("creating " + multipleChoice.getQuestionType() + " question");
		QuestionManagementService questionManagemetService = SystemConfig.instance().getQuestionManagementService();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		boolean isQuestionCreated;
		try {
			multipleChoice.setCreatedByInstructor(new User(username));
			multipleChoice.setCreationDate(new Date());
			isQuestionCreated = questionManagemetService.createMultipleChoiceQuestion(multipleChoice);
			if (isQuestionCreated) {
				attributes.addFlashAttribute("message", "Question Added Successfully");
				return "redirect:/questionmanager";
			} else {
				model.addAttribute("errormessage", "Some Error occured. Please Try Again");
				return "/error";
			}
		} catch (UserDefinedSQLException e) {
			model.addAttribute("errormessage", "Some Error occured. Please try Again");
			return "/error";
		}
	}

}
