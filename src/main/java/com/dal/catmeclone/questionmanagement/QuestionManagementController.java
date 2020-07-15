package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedSQLException;
import com.dal.catmeclone.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.logging.Logger;

@RequestMapping("/questionmanager")
@Controller
public class QuestionManagementController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
    AbstractFactory abstractFactory = SystemConfig.instance().getAbstractFactory();
    QuestionManagementAbstractFactory questionManagementAbstractFactory = abstractFactory.createQuestionManagerAbstractFactory();
    private Logger LOGGER = Logger.getLogger(QuestionManagementController.class.getName());

    /**
     * Controller Method to display question Home page with all question title
     *
     * @param model
     * @return View
     */
    @GetMapping(value = "")
    public String viewQuestionTitlePage(Model model, RedirectAttributes attributes) {

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        // getting Logged in user from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            LOGGER.info("Fetching the list of questions for Displaying on View");
            model.addAttribute("questionList", questionManagementService.getAllQuestionByUser(new User(username)));
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error message.
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }


    /**
     * Controller Method to display question Home page with all question title
     * sorted by title
     *
     * @param
     * @return View
     */
    @RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbytitle")
    public String viewQuestionSortedByTitleDB(@ModelAttribute BasicQuestion q, Model model) {

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        // getting Logged in user from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            LOGGER.info("Fetching the list of questions sorted by title");
            model.addAttribute("questionList", questionManagementService.getSortedQuestionsByTitle(new User(username)));
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error
            // message.
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }

    /**
     * Controller Method to display question Home page with all question title
     * sorted by date
     *
     * @param
     * @return View
     */
    @RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbydate")
    public String viewQuestionSortedByDate(@ModelAttribute BasicQuestion q, Model m) {

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        // getting Logged in user from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            LOGGER.info("Fetching the list of questions sorted by title");
            m.addAttribute("questionList", questionManagementService.getSortedQuestionsByDate(new User(username)));
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error
            // message.
            m.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }

    /**
     * Controller Method to delete question by given question Id
     *
     * @param
     * @return View
     */
    @PostMapping(value = "/delete")
    public String removeQuestion(@RequestParam(name = "question") int questionId, Model m, RedirectAttributes attributes) {

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        try {
            LOGGER.info("Making a call to delete Question");
            questionManagementService.deleteQuestion(questionId);
            attributes.addFlashAttribute("message", "Question Deleted Successfully");
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error message.
            attributes.addFlashAttribute("errormessage", e.getMessage());
            return "redirect:/questionmanager";
        }
        return "redirect:/questionmanager";
    }

    /**
     * Controller Method to display create question page
     *
     * @param model
     * @return View
     */
    @GetMapping("/question")
    public String getQuestionPage(Model model) {

        // Binding BasicQuestion Object to Model to display on create question page
        BasicQuestion basicQuestion = new BasicQuestion();
        model.addAttribute("BasicQuestion", basicQuestion);

        return "questionmanager/questioncreate";

    }

    /**
     * Controller Method to formulate question based on chosen question type
     *
     * @param model
     * @return View
     */
    @PostMapping("/questiondetails")
    public String getQuestionBasicDetails(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
                                          RedirectAttributes attributes) {

        LOGGER.info("Formulating questions based on selected question type");

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        // getting Logged in user from authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        basicQuestion.setCreatedByInstructor(new User(username));

        try {
            /*
             * Checking if any question already exists in database by given question title, Question Text and by the logged in user
             */
            boolean ifQuestionExist = questionManagementService.ifQuestionTitleandTextExists(basicQuestion);

            if (!ifQuestionExist) {
                if (basicQuestion.getQuestionType() == QuestionType.NUMERIC
                        || basicQuestion.getQuestionType() == QuestionType.FREETEXT) {
                    model.addAttribute("question", basicQuestion);
                    return "questionmanager/numericortextquestion";
                } else if (basicQuestion.getQuestionType() == QuestionType.MULTIPLECHOICEMANY
                        || basicQuestion.getQuestionType() == QuestionType.MULTIPLECHOICEONE) {
                    MultipleChoiceQuestion multipleChoice = new MultipleChoiceQuestion();
                    multipleChoice.setQuestionText(basicQuestion.getQuestionText());
                    multipleChoice.setQuestionTitle(basicQuestion.getQuestionTitle());
                    multipleChoice.setQuestionType(basicQuestion.getQuestionType());

                    model.addAttribute("question", multipleChoice);
                    return "questionmanager/multiplechoicequestion";
                }
            } else {
                // if Question already exist in database, returning to create question page with
                // proper error message
                attributes.addFlashAttribute("message", "Question Exists with same Question Title and Question Text");
                return "redirect:/questionmanager/question";
            }
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error
            // message.
            model.addAttribute("errormessage", "Some Error occured. Please Try Again");
            return "/error";
        }

        return "error";
    }

    /**
     * Controller Method called by AJAX/JQUERY to add new Option and bind it to
     * model attribute form data
     *
     * @param model
     * @return View
     */
    @PostMapping(params = "addOption", path = {"/question", "/order/{id}"})
    public String addOption(MultipleChoiceQuestion question, HttpServletRequest request,
                            final BindingResult bindingResult, Model model) {

        // fetching current list of option and adding a new option to the list of option
        question.getOptionList().add(new Option(question.getOptionList().size() + 1));
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            model.addAttribute("question", question);
            // If It is an Ajax request, render only #details fragment of the page.
            return "questionmanager/multiplechoicequestion::#details";
        } else {
            // If It is a standard HTTP request, render whole page.
            return "questionmanager/multiplechoicequestion";
        }
    }

    /**
     * Controller Method to create numeric and free text question
     *
     * @param model
     * @return View
     */
    @RequestMapping(value = "question/numericorfreetext", method = RequestMethod.POST)
    public String createNumericQuestion(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
                                        RedirectAttributes attributes) {

        LOGGER.info("Creating " + basicQuestion.getQuestionType() + " question");

        // Getting QuestionManagementService Instance
        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            basicQuestion.setCreatedByInstructor(new User(username));
            basicQuestion.setCreationDate(new Date());
            // Calling Service layer to create NumericOrTextQuestion
            boolean isQuestionCreated = questionManagementService.createNumericOrTextQuestion(basicQuestion);
            if (isQuestionCreated) {
                // If question is created successfully, return to question manager home page
                // with success message
                attributes.addFlashAttribute("message", "Question Added Successfully");
                return "redirect:/questionmanager";
            } else {
                // If question is not created, return to error page with generic error message
                model.addAttribute("errormessage", "Some Error occured. Please Try Again");
                return "/error";
            }
        } catch (UserDefinedSQLException e) {
            // Handling Error occurred by throwing to Error view with user defined error
            // message.
            model.addAttribute("errormessage", "Some Error occured. Please Try Again");
            return "/error";
        }
    }

    /**
     * Controller Method to create multiple choice question
     *
     * @param model
     * @return View
     */
    @RequestMapping(value = "question/multiplechoice", method = RequestMethod.POST)
    public String createMultipleChoiceQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice,
                                               Model model, RedirectAttributes attributes) {

        LOGGER.info("creating " + multipleChoice.getQuestionType() + " question");

        if (multipleChoice.areAllOptionValid()) {

            //Getting QuestionManagementService Instance
            QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isQuestionCreated;
            try {

                multipleChoice.setCreatedByInstructor(new User(username));
                multipleChoice.setCreationDate(new Date());
                // Calling Service layer to create multiple choice question
                LOGGER.info("Calling Service layer to create multiple choice question");
                isQuestionCreated = questionManagementService.createMultipleChoiceQuestion(multipleChoice);
                if (isQuestionCreated) {
                    // If question is created successfully, return to question manager home page
                    // with success message
                    attributes.addFlashAttribute("message", "Question Added Successfully");
                    return "redirect:/questionmanager";
                } else {
                    // If question is not created, return to error page with generic error message
                    model.addAttribute("errormessage", "Some Error occured. Please Try Again");
                    return "/error";
                }
            } catch (UserDefinedSQLException e) {
                // Handling Error occurred by throwing to Error view with user defined error
                // message.
                model.addAttribute("errormessage", "Some Error occured. Please try Again");
                return "/error";
            }
        } else {
            MultipleChoiceQuestion question = new MultipleChoiceQuestion();
            question.setQuestionTitle(question.getQuestionTitle());
            question.setQuestionText(question.getQuestionText());
            question.setQuestionType(question.getQuestionType());

            model.addAttribute("question", multipleChoice);
            model.addAttribute("message", "All Options are invalid. Please check and try again");
            return "questionmanager/multiplechoicequestion";
        }
    }

}
