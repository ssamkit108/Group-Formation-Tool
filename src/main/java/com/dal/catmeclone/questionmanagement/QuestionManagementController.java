package com.dal.catmeclone.questionmanagement;

import com.dal.catmeclone.AbstractFactory;
import com.dal.catmeclone.SystemConfig;
import com.dal.catmeclone.exceptionhandler.UserDefinedException;
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

    @GetMapping(value = "")
    public String viewQuestionTitlePage(Model model, RedirectAttributes attributes) {

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            LOGGER.info("Fetching the list of questions for Displaying on View");
            model.addAttribute("questionList", questionManagementService.getAllQuestionByUser(new User(username)));
        } catch (UserDefinedException e) {
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbytitle")
    public String viewQuestionSortedByTitleDB(@ModelAttribute BasicQuestion q, Model model) {

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            LOGGER.info("Fetching the list of questions sorted by title");
            model.addAttribute("questionList", questionManagementService.getSortedQuestionsByTitle(new User(username)));
        } catch (UserDefinedException e) {
            model.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, params = "action=sortbydate")
    public String viewQuestionSortedByDate(@ModelAttribute BasicQuestion q, Model m) {

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            LOGGER.info("Fetching the list of questions sorted by title");
            m.addAttribute("questionList", questionManagementService.getSortedQuestionsByDate(new User(username)));
        } catch (UserDefinedException e) {
            m.addAttribute("errormessage", e.getLocalizedMessage());
            return "error";
        }
        return "questionmanager/questionmanagerhome";
    }

    @PostMapping(value = "/delete")
    public String removeQuestion(@RequestParam(name = "question") int questionId, Model m, RedirectAttributes attributes) {

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        try {
            LOGGER.info("Making a call to delete Question");
            questionManagementService.deleteQuestion(questionId);
            attributes.addFlashAttribute("message", "Question Deleted Successfully");
        } catch (UserDefinedException e) {
            attributes.addFlashAttribute("errormessage", e.getMessage());
            return "redirect:/questionmanager";
        }
        return "redirect:/questionmanager";
    }

    @GetMapping("/question")
    public String getQuestionPage(Model model) {

        BasicQuestion basicQuestion = new BasicQuestion();
        model.addAttribute("BasicQuestion", basicQuestion);

        return "questionmanager/questioncreate";

    }

    @PostMapping("/questiondetails")
    public String getQuestionBasicDetails(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
                                          RedirectAttributes attributes) {

        LOGGER.info("Formulating questions based on selected question type");

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        basicQuestion.setCreatedByInstructor(new User(username));

        try {
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
                attributes.addFlashAttribute("message", "Question Exists with same Question Title and Question Text");
                return "redirect:/questionmanager/question";
            }
        } catch (UserDefinedException e) {
            model.addAttribute("errormessage", "Some Error occured. Please Try Again");
            return "/error";
        }
        return "error";
    }

    @PostMapping(params = "addOption", path = {"/question", "/order/{id}"})
    public String addOption(MultipleChoiceQuestion question, HttpServletRequest request,
                            final BindingResult bindingResult, Model model) {

        question.getOptionList().add(new Option(question.getOptionList().size() + 1));
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            model.addAttribute("question", question);
            return "questionmanager/multiplechoicequestion::#details";
        } else {
            return "questionmanager/multiplechoicequestion";
        }
    }

    @RequestMapping(value = "question/numericorfreetext", method = RequestMethod.POST)
    public String createNumericQuestion(@ModelAttribute("question") BasicQuestion basicQuestion, Model model,
                                        RedirectAttributes attributes) {

        LOGGER.info("Creating " + basicQuestion.getQuestionType() + " question");

        QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            basicQuestion.setCreatedByInstructor(new User(username));
            basicQuestion.setCreationDate(new Date());
            boolean isQuestionCreated = questionManagementService.createNumericOrTextQuestion(basicQuestion);
            if (isQuestionCreated) {
                attributes.addFlashAttribute("message", "Question Added Successfully");
                return "redirect:/questionmanager";
            } else {
                model.addAttribute("errormessage", "Some Error occured. Please Try Again");
                return "/error";
            }
        } catch (UserDefinedException e) {
            model.addAttribute("errormessage", "Some Error occured. Please Try Again");
            return "/error";
        }
    }

    @RequestMapping(value = "question/multiplechoice", method = RequestMethod.POST)
    public String createMultipleChoiceQuestion(@ModelAttribute("question") MultipleChoiceQuestion multipleChoice,
                                               Model model, RedirectAttributes attributes) {

        LOGGER.info("creating " + multipleChoice.getQuestionType() + " question");

        if (multipleChoice.areAllOptionValid()) {

            QuestionManagementService questionManagementService = questionManagementAbstractFactory.createQuestionManagementService();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isQuestionCreated;
            try {

                multipleChoice.setCreatedByInstructor(new User(username));
                multipleChoice.setCreationDate(new Date());
                LOGGER.info("Calling Service layer to create multiple choice question");
                isQuestionCreated = questionManagementService.createMultipleChoiceQuestion(multipleChoice);
                if (isQuestionCreated) {
                    attributes.addFlashAttribute("message", "Question Added Successfully");
                    return "redirect:/questionmanager";
                } else {
                    model.addAttribute("errormessage", "Some Error occured. Please Try Again");
                    return "/error";
                }
            } catch (UserDefinedException e) {
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
