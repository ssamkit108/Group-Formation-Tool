package com.dal.catmeclone.surveycreation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.BasicQuestion;
import com.dal.catmeclone.model.Course;
import com.dal.catmeclone.model.ModelAbstractFactory;
import com.dal.catmeclone.model.Survey;
import com.dal.catmeclone.model.SurveyQuestion;
import com.dal.catmeclone.model.User;
import com.dal.catmeclone.questionmanagement.QuestionManagementAbstractFactory;
import com.dal.catmeclone.questionmanagement.QuestionManagementService;

@RequestMapping("/survey")
@Controller
public class CourseAdminSurveyController {

	private Logger LOGGER = Logger.getLogger(CourseAdminSurveyController.class.getName());
	AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
	SurveyCreationAbstractFactory surveyCreationAbstractFactory = abstractFactory.createSurveyCreationAbstractFactory();
	QuestionManagementAbstractFactory questionManagementAbstractFactory = abstractFactory.createQuestionManagerAbstractFactory();
	ModelAbstractFactory modelAbstractFactory = abstractFactory.createModelAbstractFactory();

	//AJAX Details to handle AJAX call
	private static final String AJAX_HEADER_NAME = "X-Requested-With";
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

	
	@GetMapping("/manage")
	private String displaySurveyManagementPage(Model model, HttpSession session) {

		QuestionManagementService questionManagementService = questionManagementAbstractFactory
				.createQuestionManagementService();
		CourseAdminSurveyService courseAdminSurveyService = surveyCreationAbstractFactory.createSurveyCreationService();

		// getting Logged in user from authentication object
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Course course = (Course) session.getAttribute("course");

		try {
			LOGGER.info("Fetching the SurveyDetails to display on the view");
			Survey survey = courseAdminSurveyService.getSurveyDetailsForCourse(course);

			if (!survey.isPublishedStatus()) {
				LOGGER.info("Fetching the list of questions from question bank for Displaying on View");
				model.addAttribute("questionList", questionManagementService.getAllQuestionByUser(new User(username)));
				model.addAttribute("question", new BasicQuestion());
				model.addAttribute("unsavedchanges", false);
			}
			
			model.addAttribute("survey", survey);

		} catch (UserDefinedSQLException e) {
			// Handling Error occurred by throwing to Error view with user defined error message.
			model.addAttribute("errormessage", e.getLocalizedMessage());
			return "error";
		}
		return "survey/surveymanagement";
	}

	/**
	 * Controller Method called by AJAX/JQUERY to add new question to survey and bind it to model attribute form data
	 * @param model
	 * @return View
	 */
	@PostMapping(params = "addQuestion", path = { "/manage" })
	public String addQuestion(@ModelAttribute Survey survey, @RequestParam int questionId,
			@RequestParam String questionTitle, @RequestParam String questionText, @RequestParam String questionType,
			HttpServletRequest request, final BindingResult bindingResult, Model model) {

		BasicQuestion newQuestionForSurvey = new BasicQuestion(questionId, questionTitle, questionText, questionType);
		// fetching current list of questions and add question the list of survey question
		if (survey.getSurveyQuestions() == null) {
			ArrayList<SurveyQuestion> questions = new ArrayList<SurveyQuestion>();
			LOGGER.info("Adding Question: " +questionId+ " to Survey "+survey.getSurveyId());
			questions.add(new SurveyQuestion(newQuestionForSurvey));
			survey.setSurveyQuestions(questions);
		} else {
			LOGGER.info("Adding Question: " +questionId+ " to Survey "+survey.getSurveyId());
			survey.getSurveyQuestions().add(new SurveyQuestion(newQuestionForSurvey));
		}
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			model.addAttribute("survey", survey);
			model.addAttribute("question", new BasicQuestion());
			model.addAttribute("unsavedchanges", true);
			// If It is an Ajax request, render only #details fragment of the page.
			return "survey/surveymanagement::#details";
		} else {
			// If It is a standard HTTP request, render whole page.
			return "survey/surveymanagement";
		}
	}

	/**
	 * Controller Method called by AJAX/JQUERY to remove question from survey and bind it to model attribute form data
	 * @param model
	 * @return View
	 */
	@PostMapping(params = "removeQuestion", path = { "/manage" })
	public String removeQuestion(@ModelAttribute Survey survey, @RequestParam("removeQuestion") int questionId,
			HttpServletRequest request, final BindingResult bindingResult, Model model) {

		List<SurveyQuestion> listOfSurveyQuestion = survey.getSurveyQuestions();
		Iterator<SurveyQuestion> surveyQuestionIterator = listOfSurveyQuestion.iterator();
		// fetching current list of questions and remove question from the list of survey question
		LOGGER.info("Adding Question: " +questionId+ " to Survey "+survey.getSurveyId());
		while (surveyQuestionIterator.hasNext()) {
			SurveyQuestion surveyQuestion = surveyQuestionIterator.next();
			if (surveyQuestion.getQuestionDetail().getQuestionId() == questionId) {
				surveyQuestionIterator.remove();
				break;
			}
		}
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			model.addAttribute("survey", survey);
			model.addAttribute("question", new BasicQuestion());
			model.addAttribute("unsavedchanges", true);
			// If It is an Ajax request, render only #details fragment of the page.
			return "survey/surveymanagement::#details";
		} else {
			// If It is a standard HTTP request, render whole page.
			return "survey/surveymanagement";
		}
	}

	/**
	 * Controller Method to save and update the survey into database
	 * @param Survey
	 * @return View
	 */
	@PostMapping("/save")
	private String saveSurvey(@ModelAttribute Survey survey, Model model, RedirectAttributes attributes) {

		CourseAdminSurveyService courseAdminSurveyService = surveyCreationAbstractFactory.createSurveyCreationService();

		if (survey.getSurveyQuestions() == null) {
			attributes.addFlashAttribute("errormessage", "Atleast add one question to survey.");
			model.addAttribute("unsavedchanges", false);
			return "redirect:/survey/manage";
		}
		try {
			LOGGER.info("Calling Services to save or update the survey details");
			courseAdminSurveyService.saveSurvey(survey);
			model.addAttribute("unsavedchanges", false);
			attributes.addFlashAttribute("message", "Survey updated and saved Successfully.");
			return "redirect:/survey/manage";
		} catch (UserDefinedSQLException e) {

			attributes.addFlashAttribute("errormessage", "Some Error occurred. Couldn't save survey.");
			return "redirect:/survey/manage";
		}

	}

	/**
	 * Controller Method to publish the survey
	 * @param Survey
	 * @return View
	 */
	@PostMapping("/publish")
	private String publishSurvey(@RequestParam int surveyId, Model model, RedirectAttributes attributes) {

		CourseAdminSurveyService courseAdminSurveyService = surveyCreationAbstractFactory.createSurveyCreationService();
		boolean published;
		try {
			LOGGER.info("Calling Services to publish the survey details");
			published = courseAdminSurveyService.publishSurvey(surveyId);
			if (published) {
				attributes.addFlashAttribute("message", "Survey Published for the Course Successfully.");
			} else {
				attributes.addFlashAttribute("errormessage",
						"Couldn't publish Survey. Please verify surveydetails and try again");
			}
			return "redirect:/survey/manage";
		} catch (UserDefinedSQLException e) {
		
			attributes.addFlashAttribute("errormessage", "Some Error occurred. Couldn't save survey.");
			return "redirect:/survey/manage";
		}

	}

}
